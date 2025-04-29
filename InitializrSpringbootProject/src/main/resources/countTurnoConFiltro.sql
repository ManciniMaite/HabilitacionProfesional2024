DROP FUNCTION IF EXISTS public.contar_turnos_con_filtros;
CREATE OR REPLACE FUNCTION public.contar_turnos_con_filtros(
	p_animal_ids text DEFAULT NULL::text,
	p_veterinaria_id bigint DEFAULT NULL::bigint,
	p_veterinario_id bigint DEFAULT NULL::bigint,
	p_estado_id bigint DEFAULT NULL::bigint,
	p_fecha date DEFAULT NULL::date)
    RETURNS bigint
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
    sql TEXT;
    conds TEXT := 'WHERE 1=1';
    total BIGINT;
BEGIN
    -- Condiciones dinámicas
    IF p_animal_ids IS NOT NULL THEN
        conds := conds || ' AND t.animal_id = ANY (string_to_array($1, '','')::BIGINT[])';
    END IF;

    IF p_veterinaria_id IS NOT NULL THEN
        conds := conds || ' AND t.veterinaria_id = ' || p_veterinaria_id;
    END IF;

    IF p_veterinario_id IS NOT NULL THEN
        conds := conds || ' AND t.veterinario_id = ' || p_veterinario_id;
    END IF;

    IF p_estado_id IS NOT NULL THEN
        conds := conds || ' AND t.estado_id = ' || p_estado_id;
    END IF;

    IF p_fecha IS NOT NULL THEN
	    conds := conds || ' AND DATE(t.fecha_hora) = ''' || p_fecha || '''';
	ELSE
	    conds := conds || ' AND t.fecha_hora >= NOW()';
	END IF;

    sql := 'SELECT COUNT(*) FROM turno t ' || conds;

    EXECUTE sql INTO total USING p_animal_ids;
    RETURN total;
END;
$BODY$;