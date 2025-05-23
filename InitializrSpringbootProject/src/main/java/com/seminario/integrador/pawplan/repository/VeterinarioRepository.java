/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import com.seminario.integrador.pawplan.controller.values.ProfesionalPorVeterinaria;
import com.seminario.integrador.pawplan.controller.values.VeterinarioXciudad;
import com.seminario.integrador.pawplan.model.Veterinario;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sebastian
 */
@Repository
public interface VeterinarioRepository extends CrudRepository<Veterinario, Long>{
    
	
	@Query(value = """
	        select distinct
				
				case when razon_social is null then 
					u.nombre||' '||u.apellido 
				else 
					u.razon_social
				end as nombre,
				
				case when (es_independiente=false or hace_domicilio = false) then
					d.calle||' '||d.numero||', '||c.nombre
				else
					null
				end as domicilio,
					
					u.telefono
				
				from usuario u 
					inner join domicilio d ON d.usuario_id = u.id
					inner join ciudad c ON c.id = d.ciudad_id
					inner join Dia_Horario_Atencion dh ON u.id = dh.id_usuario
				    inner join Horario h ON h.dia_horario_atencion_id = dh.id
				where 
					(u.hace_guardia = true and c.id= :idCiudad ) 
					or 
					(CURRENT_TIME BETWEEN 
						REPLACE(REPLACE(h.hora_inicio, ' AM', ''), ' PM', '')::time AND 
    					REPLACE(REPLACE(h.hora_fin, ' AM', ''), ' PM', '')::time
					AND upper(dh.dia) = (
				              CASE EXTRACT(DOW FROM now())
				                  WHEN 0 THEN 'DOMINGO'
				                  WHEN 1 THEN 'LUNES'
				                  WHEN 2 THEN 'MARTES'
				                  WHEN 3 THEN 'MIERCOLES'
				                  WHEN 4 THEN 'JUEVES'
				                  WHEN 5 THEN 'VIERNES'
				                  WHEN 6 THEN 'SABADO'
				              END
	         		) and c.id= :idCiudad)
	    """, nativeQuery = true)
	List<Object[]> findByCiudadHorario(@Param("idCiudad") Long idCiudad);
	
	@Query(value = """
        SELECT 
			u.id,
			u.nombre,
			u.apellido
        FROM usuario u
        JOIN veterinario_tipo_especie ve ON ve.veterinario_id = u.id
        JOIN domicilio d ON d.usuario_id = u.id
        JOIN ciudad c ON d.ciudad_id = c.id
        WHERE c.id = :idCiudad
        AND ve.tipo_especie_id = :idTipoEspecie
        AND u.hace_domicilio = :domicilio
        AND u.es_independiente = true
        AND TRIM(u.dtype) = 'Veterinario';
    """, nativeQuery = true)
    ArrayList<VeterinarioXciudad> findByCiudad(@Param("idCiudad") Long idCiudad, @Param("idTipoEspecie") Long idTipoEspecie, @Param("domicilio") boolean domicilio);

    List<ProfesionalPorVeterinaria> findByVeterinariaId(@Param("idVeterinaria") Long idVeterinaria);

    @Query(value = """
        SELECT 
            u.id,
            u.nombre,
            u.apellido
        FROM usuario u
        JOIN veterinario_tipo_especie ve ON ve.veterinario_id = u.id
        WHERE u.veterinaria_id = :idVeterinaria
        AND ve.tipo_especie_id = :idTipoEspecie
        AND TRIM(u.dtype) = 'Veterinario';
    """, nativeQuery = true)
    ArrayList<VeterinarioXciudad> findByVeterinariaAndTipoEspecie(@Param("idVeterinaria") Long idVeterinaria, @Param("idTipoEspecie") Long idTipoEspecie);

}


