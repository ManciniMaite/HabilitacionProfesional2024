DROP FUNCTION IF EXISTS public.buscar_turnos_con_filtros_paginado_dinamico;
CREATE OR REPLACE FUNCTION public.buscar_turnos_con_filtros_paginado_dinamico(
    p_animal_ids TEXT DEFAULT NULL,
    p_veterinaria_id BIGINT DEFAULT NULL,
    p_veterinario_id BIGINT DEFAULT NULL,
    p_estado_id BIGINT DEFAULT NULL,
    p_fecha DATE DEFAULT NULL,
    p_page INTEGER DEFAULT 0,
    p_size INTEGER DEFAULT 10,
    p_order_by TEXT DEFAULT 'fecha_hora',
    p_order_dir TEXT DEFAULT 'desc'
)
RETURNS TABLE (
    id BIGINT,
    id_animal BIGINT,
    nombre_animal VARCHAR,
    id_veterinario BIGINT,
    nombre_veterinario VARCHAR,
    id_veterinaria BIGINT,
    nombre_veterinaria VARCHAR,
    especie VARCHAR,
    raza VARCHAR,
    fecha TIMESTAMP,
    estado_id BIGINT 
)AS $$
DECLARE
    sql TEXT;
    conds TEXT := 'WHERE 1=1';
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
    END IF;

    -- Validaciones básicas de ordenamiento
    IF NOT p_order_by ~ '^[a-zA-Z_]+$' THEN
        RAISE EXCEPTION 'Campo order_by inválido';
    END IF;
    IF LOWER(p_order_dir) NOT IN ('asc', 'desc') THEN
        RAISE EXCEPTION 'Dirección de ordenamiento inválida';
    END IF;

    sql := 'SELECT 
        t.id,
        a.id,
        a.nombre,
        v.id,
        (v.nombre || '' '' || v.apellido)::VARCHAR,
        vet.id,
        vet.razon_social,
        e.nombre,
        r.nombre,
        t.fecha_hora,
		t.estado_id
    FROM turno t
    LEFT JOIN animal a ON a.id = t.animal_id
    LEFT JOIN usuario v ON v.id = t.veterinario_id
    LEFT JOIN usuario vet ON vet.id = t.veterinaria_id
    LEFT JOIN raza r ON r.id = a.raza_id
    LEFT JOIN especie e ON e.id = r.especie_id
    ' || conds || 
    ' ORDER BY t.' || p_order_by || ' ' || p_order_dir || 
    ' LIMIT ' || p_size || ' OFFSET ' || (p_page * p_size);
	
    RETURN QUERY EXECUTE sql USING p_animal_ids;
END;
$$ LANGUAGE plpgsql;


