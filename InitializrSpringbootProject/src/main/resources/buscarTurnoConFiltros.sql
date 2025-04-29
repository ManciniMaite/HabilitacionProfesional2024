DROP FUNCTION IF EXISTS public.buscar_turnos_con_filtros_paginado_dinamico;
CREATE OR REPLACE FUNCTION public.buscar_turnos_con_filtros_paginado_dinamico(
	p_animal_ids text DEFAULT NULL::text,
	p_veterinaria_id bigint DEFAULT NULL::bigint,
	p_veterinario_id bigint DEFAULT NULL::bigint,
	p_estado_id bigint DEFAULT NULL::bigint,
	p_fecha date DEFAULT NULL::date,
	p_page integer DEFAULT 0,
	p_size integer DEFAULT 10,
	p_order_by text DEFAULT 'fecha_hora'::text,
	p_order_dir text DEFAULT 'desc'::text)
    RETURNS TABLE(id bigint, id_animal bigint, nombre_animal character varying, id_veterinario bigint, nombre_veterinario character varying, id_veterinaria bigint, nombre_veterinaria character varying, especie character varying, raza character varying, fecha timestamp without time zone, estado_id bigint) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
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
	ELSE
	    conds := conds || ' AND t.fecha_hora >= NOW()';
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
$BODY$;