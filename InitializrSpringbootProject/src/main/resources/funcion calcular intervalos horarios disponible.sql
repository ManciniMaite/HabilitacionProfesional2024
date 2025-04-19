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
    hora_inicio TIME;
    hora_fin TIME;
    intervalo_actual TIME;
    horarios_json JSONB := '[]';
    duracion_predet TIME;
	minutos_actuales INTEGER ;
    hora_actual TIME;
	duracion_minutos INTEGER;
	minutos_redondeados INTEGER;
BEGIN
    -- Determinar si es veterinario o veterinaria
    SELECT  CASE 
            WHEN u.dtype ILIKE 'VETERINARIA' THEN '00:30:00'::TIME
            WHEN u.dtype ILIKE 'VETERINARIO' THEN '01:00:00'::TIME
        END,
        CASE
            WHEN u.dtype ILIKE 'VETERINARIA' THEN 30
            WHEN u.dtype ILIKE 'VETERINARIO' THEN 60
        END
    INTO duracion_predet, duracion_minutos
    FROM Usuario u WHERE u.id = p_usuario_id;

    IF duracion_predet IS NULL THEN
        RAISE EXCEPTION 'Usuario no encontrado o sin rol definido';
    END IF;

    -- Obtener los horarios de atención del usuario
    FOR hora_inicio, hora_fin IN
        SELECT h.hora_inicio, h.hora_fin
        FROM Dia_Horario_Atencion d
        JOIN Usuario u ON u.id = d.id_usuario
        JOIN Horario h ON h.dia_horario_atencion_id = d.id
        WHERE u.id = p_usuario_id
        AND upper(d.dia) = (SELECT CASE TO_CHAR(p_dia, 'D')
                             WHEN '1' THEN 'DOMINGO'
                             WHEN '2' THEN 'LUNES'
                             WHEN '3' THEN 'MARTES'
                             WHEN '4' THEN 'MIERCOLES'
                             WHEN '5' THEN 'JUEVES'
                             WHEN '6' THEN 'VIERNES'
                             WHEN '7' THEN 'SABADO'
                           END)
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
			    WHERE (t.veterinario_id = p_usuario_id OR t.veterinaria_id = p_usuario_id)
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