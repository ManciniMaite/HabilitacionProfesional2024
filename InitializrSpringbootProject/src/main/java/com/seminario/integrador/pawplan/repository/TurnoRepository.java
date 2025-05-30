/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.seminario.integrador.pawplan.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.integrador.pawplan.controller.values.TurnoFb;
import com.seminario.integrador.pawplan.model.Animal;
import com.seminario.integrador.pawplan.model.Estado;
import com.seminario.integrador.pawplan.model.Turno;
import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;

/**
 *
 * @author sebastian
 */
@Repository
public interface TurnoRepository extends CrudRepository<Turno, Long>{
    
	@Query("select calcular_turnos_disponibles(:clienteId, :fecha )")
	public String consultarTurnosDisponibles(@Param("clienteId") Long clienteId,@Param("fecha") Date fecha);

    List<Turno> findByVeterinariaAndEstado(Veterinaria veterinaria, Estado estado);

    List<Turno> findByVeterinarioAndEstado(Veterinario veterinario, Estado estado);

    @Query("SELECT t FROM Turno t " +
               //"WHERE (t.veterinario = :veterinario OR t.veterinaria = :veterinaria OR (t.veterinario = :veterinario AND t.veterinaria = :veterinaria) ) " +
               "WHERE t.veterinario = :veterinario "+ 
               "AND t.fechaHora = :fechaHora " +
               "AND t.estado.nombre IN :estados")
    List<Turno> buscarTurnosPorVeterinariaVeterinarioYFechaYEstado(
            @Param("veterinario") Veterinario veterinario,
            @Param("fechaHora") Date fechaHora,
            @Param("estados") List<String> estados);


    @Query("SELECT t FROM Turno t " +
            //"WHERE (t.veterinario = :veterinario OR t.veterinaria = :veterinaria OR (t.veterinario = :veterinario AND t.veterinaria = :veterinaria) ) " +
            "WHERE t.animal = :animal "+ 
            "AND DATE(t.fechaHora) = DATE( :fechaHora ) " +
            "AND t.estado.nombre IN :estados")
    List<Turno> buscarTurnosPorAnimalYFechaYEstado(
         @Param("animal") Animal animal,
         @Param("fechaHora") Date fechaHora,
         @Param("estados") List<String> estados);
    
    @Query(value = "SELECT * FROM buscar_turnos_con_filtros_paginado_dinamico(:animalIds, :veterinariaId, :veterinarioId, :estadoId, :fecha, :page, :size, :orderBy, :orderDir)", nativeQuery = true)
    List<TurnoFb> buscarTurnosPagina(
        @Param("animalIds") String animalIds,
        @Param("veterinariaId") Long veterinariaId,
        @Param("veterinarioId") Long veterinarioId,
        @Param("estadoId") Long estadoId,
        @Param("fecha") LocalDate fecha,
        @Param("page") int page,
        @Param("size") int size,
        @Param("orderBy") String orderBy,
        @Param("orderDir") String orderDir
    );

    @Query(value = "SELECT contar_turnos_con_filtros(:animalIds, :veterinariaId, :veterinarioId, :estadoId, :fecha)", nativeQuery = true)
    Long contarTurnos(
        @Param("animalIds") String animalIds,
        @Param("veterinariaId") Long veterinariaId,
        @Param("veterinarioId") Long veterinarioId,
        @Param("estadoId") Long estadoId,
        @Param("fecha") LocalDate fecha
    );

    //TRAE SOLO LOS ANIMALES QUE ATENDIO LE VETERINARIE PARA EL FILTRO DE LA ADM DE TURNO
    @Query(value = """
        SELECT 
            u.id AS cliente_id,
            u.nombre AS cliente_nombre,
            u.apellido AS cliente_apellido,
            u.dni as dni,
            json_agg(
                DISTINCT jsonb_build_object(
                    'animal_id', a.id,
                    'animal_nombre', a.nombre,
                    'raza_nombre', r.nombre,
                    'especie_nombre', e.nombre
                )
            ) AS animales
        FROM turno t
        LEFT JOIN animal a ON a.id = t.animal_id
        LEFT JOIN raza r ON r.id = a.raza_id
        LEFT JOIN especie e ON e.id = r.especie_id
        LEFT JOIN usuario u ON u.id = a.id_cliente
        WHERE ( 
                (:tipoFiltro = 'VETERINARIA' AND t.veterinaria_id = :idFiltro)
                OR (:tipoFiltro = 'VETERINARIO' AND t.veterinario_id = :idFiltro)
            )
        AND u.nombre IS NOT NULL 
        AND u.apellido IS NOT NULL
        GROUP BY u.id, u.nombre, u.apellido
        ORDER BY u.id
    """, nativeQuery = true)
    List<Map<String, Object>> findClientesConAnimalesByFiltro(@Param("tipoFiltro") String tipoFiltro, @Param("idFiltro") Long idFiltro);


    //TRAR TODOS LOS CLIENTES DE LA VETERINARIA CON SUS ANIMALES PARA PODER CARGAR UN NUEVO TURNO
    @Query(value = """
        SELECT 
            u.id AS cliente_id,
            u.nombre AS cliente_nombre,
            u.apellido AS cliente_apellido,
            u.dni AS dni,
            json_agg(
                DISTINCT jsonb_build_object(
                    'animal_id', a.id,
                    'animal_nombre', a.nombre,
                    'raza_nombre', r.nombre,
                    'especie_nombre', e.nombre
                )
            ) AS animales
        FROM usuario u
        LEFT JOIN animal a ON a.id_cliente = u.id
        LEFT JOIN raza r ON r.id = a.raza_id
        LEFT JOIN especie e ON e.id = r.especie_id
        WHERE u.id IN (
            SELECT DISTINCT u_sub.id
            FROM turno t_sub
            JOIN animal a_sub ON a_sub.id = t_sub.animal_id
            JOIN usuario u_sub ON u_sub.id = a_sub.id_cliente
            WHERE (
                    (:tipoFiltro = 'VETERINARIA' AND t_sub.veterinaria_id = :idFiltro)
                    OR (:tipoFiltro = 'VETERINARIO' AND t_sub.veterinario_id = :idFiltro)
                )
            AND u_sub.nombre IS NOT NULL 
            AND u_sub.apellido IS NOT NULL
        )
        GROUP BY u.id
        ORDER BY u.id
    """, nativeQuery = true)
    List<Map<String, Object>> findClientesConAnimalesCompleto(@Param("tipoFiltro") String tipoFiltro, @Param("idFiltro") Long idFiltro);


    List<Turno> findByAnimalIdAndEstadoIdIn(Long animalId, List<Long> estadoIds);
}


