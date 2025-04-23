DROP FUNCTION IF EXISTS public.contar_turnos_con_filtros;
CREATE OR REPLACE FUNCTION public.contar_turnos_con_filtros(
    p_animal_ids TEXT DEFAULT NULL,
    p_veterinaria_id BIGINT DEFAULT NULL,
    p_veterinario_id BIGINT DEFAULT NULL,
    p_estado_id BIGINT DEFAULT NULL,
    p_fecha DATE DEFAULT NULL
)
RETURNS BIGINT AS $$
DECLARE
    sql TEXT;
    conds TEXT := 'WHERE 1=1';
    total BIGINT;
BEGIN
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
    END IF;

    sql := 'SELECT COUNT(*) FROM turno t ' || conds;

    EXECUTE sql INTO total USING p_animal_ids;
    RETURN total;
END;
$$ LANGUAGE plpgsql;