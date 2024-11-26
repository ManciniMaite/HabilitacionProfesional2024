CREATE OR REPLACE FUNCTION public.calcular_turnos_disponibles(
	p_usuario_id bigint,
	p_dia date,
	p_duracion integer DEFAULT 0)
    RETURNS jsonb
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
    hora_inicio TIME;
    hora_fin TIME;
    intervalo_actual TIME;
    duracion INTERVAL;
	duracion_predet INTERVAL := '10 minutes';
	horarios_json JSONB :='[]';
BEGIN

	IF (p_duracion<>0) THEN
		duracion := (p_duracion ||' minutes')::interval;
	ELSE
		duracion := (10 ||' minutes')::interval;
	END IF;
	raise notice 'DURACION. %',duracion;
    -- Obtener los horarios de atención del usuario
    FOR hora_inicio, hora_fin IN
        SELECT h.hora_inicio, h.hora_fin
        FROM Dia_Horario_Atencion d
        JOIN Usuario u ON u.id = p_usuario_id
        JOIN Horario h ON h.id = d.horario_id
        WHERE u.id = p_usuario_id
        AND d.dia = (	SELECT CASE TO_CHAR(p_dia, 'D')
					         WHEN '1' THEN 'DOMINGO'
					         WHEN '2' THEN 'LUNES'
					         WHEN '3' THEN 'MARTES'
					         WHEN '4' THEN 'MIERCOLES'
					         WHEN '5' THEN 'JUEVES'
					         WHEN '6' THEN 'VIERNES'
					         WHEN '7' THEN 'SABADO'
					       END AS nombre_dia )

    LOOP
        -- Inicializar el intervalo actual con la hora de inicio
        intervalo_actual := hora_inicio;
		raise notice '1. %',intervalo_actual;
        -- Bucle para generar intervalos de 10 minutos entre la hora de inicio y la hora de fin
        LOOP
            EXIT WHEN intervalo_actual + duracion > hora_fin ;
			
            raise notice '1. %, in: % fin: %',intervalo_actual >= hora_fin,intervalo_actual,intervalo_actual + duracion;
            -- Verificar si el intervalo no está reservado
            IF NOT EXISTS (
                SELECT 1 
                FROM Turno t 
					INNER JOIN estado e ON e.id=t.estado_id 
                WHERE (t.veterinario_id = p_usuario_id OR t.veterinaria_id = p_usuario_id)
                AND t.fecha::DATE = p_dia
				AND
				--verifica solapamiento de intervalos
				(
    				(t.fecha::TIME, t.fecha::TIME + (t.duracion_estimada || ' minutes')::interval) 
						OVERLAPS 
					(intervalo_actual, intervalo_actual + duracion)
  				)
				  
     			AND e.nombre NOT IN ('CANCELADO')

				 
            ) THEN
                -- Si el intervalo está disponible, lo guardamos
                horarios_json := horarios_json || jsonb_build_object(
		            'horaInicio', intervalo_actual,
		            'horaFin', (intervalo_actual + duracion)
		        );
			ELSE
				RAISE NOTICE 'SE INTERSECTA CON UN TURNO';
				
            END IF;
			
            -- Avanzar x minutos
            intervalo_actual := intervalo_actual + duracion_predet;
        END LOOP;
    END LOOP;

	RETURN horarios_json;
END 
$BODY$;