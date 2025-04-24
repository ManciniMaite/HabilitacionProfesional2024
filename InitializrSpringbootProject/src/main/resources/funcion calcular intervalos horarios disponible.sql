DROP FUNCTION IF EXISTS public.calcular_turnos_disponibles;

CREATE OR REPLACE FUNCTION public.calcular_turnos_disponibles(
	p_usuario_id bigint,
	p_dia date)
    RETURNS jsonb
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
    vet_independiente BOOLEAN;
    id_veterinaria INTEGER;
    es_domicilio BOOLEAN;
    hora_inicio TIME;
    hora_fin TIME;
    intervalo_actual TIME;
    horarios_json JSONB := '[]';
    duracion_predet TIME;
	minutos_actuales INTEGER ;
    hora_actual TIME;
	duracion_minutos INTEGER;
	minutos_redondeados INTEGER;
    id_us INTEGER;
BEGIN

    SELECT es_independiente, veterinaria_id, hace_domicilio INTO vet_independiente, id_veterinaria, es_domicilio FROM usuario u WHERE u.id = p_usuario_id;

    IF vet_independiente THEN
        --duracion_turno = 1hr
        duracion_predet := '01:00:00'::TIME;
        duracion_minutos := 60;
        id_us:=p_usuario_id;
    ELSE
        id_us:=id_veterinaria;
        RAISE NOTICE 'ID DE VETERINARIA';
        RAISE NOTICE 'Value: %', id_us;
        SELECT hace_domicilio INTO es_domicilio FROM usuario u WHERE u.id = id_veterinaria;

        IF es_domicilio THEN
            --duracion 1hr
            duracion_predet := '01:00:00'::TIME;
            duracion_minutos := 60;
        ELSE
            --duracion_turno = 30min
            duracion_predet := '00:30:00'::TIME;
            duracion_minutos := 30;
        END IF;
    END IF;

    -- Determinar si es veterinario o veterinaria
    -- SELECT  CASE 
    --         WHEN u.dtype ILIKE 'VETERINARIA' THEN '00:30:00'::TIME
    --         WHEN u.dtype ILIKE 'VETERINARIO' THEN '01:00:00'::TIME
    --     END,
    --     CASE
    --         WHEN u.dtype ILIKE 'VETERINARIA' THEN 30
    --         WHEN u.dtype ILIKE 'VETERINARIO' THEN 60
    --     END
    -- INTO duracion_predet, duracion_minutos
    -- FROM Usuario u WHERE u.id = p_usuario_id;

    IF duracion_predet IS NULL THEN
        RAISE EXCEPTION 'Usuario no encontrado o sin rol definido';
    END IF;

    -- Obtener los horarios de atención del usuario
    -- El horario depende del usuairo o de la veterinaria si no es independiente por eso id_us
    FOR hora_inicio, hora_fin IN
        SELECT h.hora_inicio, h.hora_fin
        FROM Dia_Horario_Atencion d
        JOIN Usuario u ON u.id = d.id_usuario
        JOIN Horario h ON h.dia_horario_atencion_id = d.id
        WHERE u.id = id_us --Si usamos el p_usuario_id y el veterinario trabaja para una veterinaria entonces no vamos a tener el horario :)
          AND upper(d.dia) = (
              CASE EXTRACT(DOW FROM p_dia)
                  WHEN 0 THEN 'DOMINGO'
                  WHEN 1 THEN 'LUNES'
                  WHEN 2 THEN 'MARTES'
                  WHEN 3 THEN 'MIERCOLES'
                  WHEN 4 THEN 'JUEVES'
                  WHEN 5 THEN 'VIERNES'
                  WHEN 6 THEN 'SABADO'
              END
          )

    LOOP
        -- Inicializar el intervalo actual con la hora de inicio o ajustar según la hora actual
        IF p_dia = CURRENT_DATE THEN
            -- Obtener la hora actual
            hora_actual := CURRENT_TIME;
            -- Extraer los minutos de la hora actual
            minutos_actuales := EXTRACT(MINUTE FROM hora_actual);

            -- Extraer la duración del turno en minutos
        END IF;

        IF hora_actual IS NULL THEN
            hora_actual := '00:00:00'::TIME;
        END IF;

        IF hora_inicio<hora_actual THEN

            -- Calcular el redondeo al siguiente múltiplo de la duración del turno
            minutos_redondeados := CEIL(minutos_actuales::DECIMAL / duracion_minutos) * duracion_minutos;

            -- Construir el intervalo actual redondeado
            intervalo_actual := date_trunc('hour', hora_actual) + (minutos_redondeados || ' minutes')::INTERVAL;

            -- Asegurar que el intervalo actual no sea anterior a la hora actual
            IF intervalo_actual < hora_actual THEN
                intervalo_actual := intervalo_actual + (duracion_minutos || ' minutes')::INTERVAL;
            END IF;

            -- Ajuste para que el inicio sea siempre múltiplo de la duración
            intervalo_actual := DATE_TRUNC('hour', intervalo_actual) + (EXTRACT(MINUTE FROM intervalo_actual) / duracion_minutos * duracion_minutos || ' minutes')::INTERVAL;

            -- Formatear intervalo_actual para eliminar segundos y milisegundos
            intervalo_actual := (DATE_TRUNC('minute', intervalo_actual))::TIME;
        ELSE
            intervalo_actual := hora_inicio;
        END IF;

        -- Generar turnos con el intervalo determinado
		
        LOOP
            EXIT WHEN intervalo_actual >= hora_fin;

            -- Verificar si el turno está disponible

            IF NOT EXISTS (
			    SELECT 1 
			    FROM Turno t 
			    INNER JOIN estado e ON e.id = t.estado_id 
			    WHERE t.veterinario_id = p_usuario_id
			    AND t.fecha_hora::DATE = p_dia
			    AND (t.fecha_hora, t.fecha_hora + (duracion_minutos || ' minutes')::INTERVAL) 
			        OVERLAPS 
			        (p_dia + intervalo_actual, p_dia + intervalo_actual + duracion_predet)
			    AND e.nombre NOT IN ('CANCELADO')
			) THEN
			    -- Agregar turno disponible
			    horarios_json := horarios_json || jsonb_build_object(
				    'horaInicio', intervalo_actual,
				    'horaFin', intervalo_actual + duracion_predet::INTERVAL
				);
			END IF;

            -- Avanzar al siguiente turno disponible
            intervalo_actual := intervalo_actual + duracion_predet::INTERVAL;
        END LOOP;
    END LOOP;

    RETURN horarios_json;
END 
$BODY$;