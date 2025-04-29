package com.seminario.integrador.pawplan.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.integrador.pawplan.controller.values.PorcentajeTurnosAtendAceptReserPorVeterinario;
import com.seminario.integrador.pawplan.controller.values.ReportePorcentajeEstado;
import com.seminario.integrador.pawplan.controller.values.ReporteTurnoPorEspecie;
import com.seminario.integrador.pawplan.controller.values.turnosPorVeterinario;
import com.seminario.integrador.pawplan.model.Turno;

@Repository
public interface ReportesRepository extends CrudRepository<Turno, Long> {
    //Cantidad de turnos atendidos por especie
    @Query(value = """
        SELECT 
            e.nombre AS especie,
            COUNT(t.id) AS cantidad_turnos
        FROM turno t
        JOIN animal a ON t.animal_id = a.id
        JOIN raza r ON a.raza_id = r.id
        JOIN especie e ON r.especie_id = e.id
        WHERE DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
            AND  ( 
                (:tipoFiltro = 'VETERINARIA' AND t.veterinaria_id = :idFiltro)
                OR (:tipoFiltro = 'VETERINARIO' AND t.veterinario_id = :idFiltro)
            )
            AND t.estado_id=5
        GROUP BY e.nombre
        ORDER BY cantidad_turnos DESC;
    """, nativeQuery = true)
    List<ReporteTurnoPorEspecie> turnosAtendidosPorEspecie(@Param("tipoFiltro") String tipoFiltro,@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    //Porcentaje de tunos por estado
    @Query(value = """
        SELECT 
            e.nombre,
            COUNT(*) AS cantidad,
            COUNT(*) * 100.0 / SUM(COUNT(*)) OVER () AS porcentaje
        FROM turno t
        JOIN estado e on e.id=t.estado_id 
        WHERE DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
        AND ( 
                (:tipoFiltro = 'VETERINARIA' AND t.veterinaria_id = :idFiltro)
                OR (:tipoFiltro = 'VETERINARIO' AND t.veterinario_id = :idFiltro)
            )
        GROUP BY e.nombre
        ORDER BY porcentaje DESC;
    """, nativeQuery = true)
    List<ReportePorcentajeEstado> porcentajesTurnosPorEstado(@Param("tipoFiltro") String tipoFiltro,@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    //Tasa de cancelacion/rechazo
    @Query(value = """
        SELECT
            (SUM(CASE WHEN e.id IN (2, 4) THEN 1 ELSE 0 END) * 100.0) / COUNT(*) AS tasa_cancelacion_rechazo
        FROM turno t
        JOIN estado e ON e.id=t.estado_id
        WHERE DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
        AND ( 
                (:tipoFiltro = 'VETERINARIA' AND t.veterinaria_id = :idFiltro)
                OR (:tipoFiltro = 'VETERINARIO' AND t.veterinario_id = :idFiltro)
            )
    """, nativeQuery = true)
    float tasaCancelacionRechazo(@Param("tipoFiltro") String tipoFiltro,@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    //Tasa de atencion
    @Query(value = """
        SELECT
            (SUM(CASE WHEN e.id IN (5) THEN 1 ELSE 0 END) * 100.0) / COUNT(*) AS tasa_cancelacion_rechazo
        FROM turno t
        JOIN estado e ON e.id=t.estado_id
        WHERE DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
        AND ( 
                (:tipoFiltro = 'VETERINARIA' AND t.veterinaria_id = :idFiltro)
                OR (:tipoFiltro = 'VETERINARIO' AND t.veterinario_id = :idFiltro)
            )
    """, nativeQuery = true)
    float tasaDeAtencion(@Param("tipoFiltro") String tipoFiltro,@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);


    // //Turnos atendidos por veterinario
    // @Query(value = """
    //     SELECT 
    //         v.nombre || ' ' || v.apellido AS veterinario,
    //         COUNT(CASE WHEN t.estado_id IN (5) THEN 1 END) AS cantidad,
    //         COUNT(*) AS total_turnos,
    //         COUNT(CASE WHEN t.estado_id IN (5) THEN 1 END) * 100.0 / COUNT(*) AS porcentaje
    //     FROM turno t
    //     JOIN usuario v ON t.veterinario_id = v.id
    //     WHERE t.veterinaria_id = :idFiltro
    //     AND DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
    //     GROUP BY v.nombre, v.apellido
    //     ORDER BY porcentaje DESC;

    // """, nativeQuery = true)
    // List<PorcentajeTurnosAtendAceptReserPorVeterinario> atendidosPorVeterinario(@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    // //Porcentaje de turnos reservados - aceptados (pendientes de atencion) sobre el total de turnos sacados por veterinario
    // @Query(value = """
    //     SELECT 
    //         v.nombre || ' ' || v.apellido AS veterinario,
    //         COUNT(CASE WHEN e.id IN (1, 3) THEN 1 END) AS cantidad,
    //         COUNT(*) AS total_turnos,
    //         COUNT(CASE WHEN e.id IN (1, 3) THEN 1 END) * 100.0 / COUNT(*) AS porcentaje
    //     FROM turno t
    //     JOIN usuario v ON t.veterinario_id = v.id
    //     JOIN estado e ON e.id = t.estado_id
    //     WHERE t.veterinaria_id = :idFiltro
    //     AND DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
    //     GROUP BY v.id, v.nombre, v.apellido
    //     ORDER BY v.nombre, porcentaje DESC;

    // """, nativeQuery = true)
    // List<PorcentajeTurnosAtendAceptReserPorVeterinario> porcentajePorVeterinarios(@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    // //Porcentaje de turnos rechazados sobre el total de turnos sacados por veterinario
    // @Query(value = """
    //     SELECT 
    //         v.nombre || ' ' || v.apellido AS veterinario,
    //         COUNT(CASE WHEN e.id IN (4) THEN 1 END) AS cantidad,
    //         COUNT(*) AS total_turnos,
    //         COUNT(CASE WHEN e.id IN (4) THEN 1 END) * 100.0 / COUNT(*) AS porcentaje
    //     FROM turno t
    //     JOIN usuario v ON t.veterinario_id = v.id
    //     JOIN estado e ON e.id = t.estado_id
    //     WHERE t.veterinaria_id = :idFiltro
    //     AND DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
    //     GROUP BY v.id, v.nombre, v.apellido
    //     ORDER BY v.nombre, porcentaje DESC;

    // """, nativeQuery = true)
    // List<PorcentajeTurnosAtendAceptReserPorVeterinario> porcentajeRechazadosPorVeterinarios(@Param("idFiltro") Long idFiltro, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query(value = """
        SELECT
            v.nombre || ' ' || v.apellido AS veterinario,
            COUNT(CASE WHEN t.estado_id = ANY(string_to_array(:estados, ',')::BIGINT[]) THEN 1 END) AS cantidad,
            COUNT(*) AS total_turnos,
            CASE
                WHEN COUNT(*) > 0 THEN COUNT(CASE WHEN t.estado_id = ANY(string_to_array(:estados, ',')::BIGINT[]) THEN 1 END) * 100.0 / COUNT(*)
                ELSE 0
            END AS porcentaje
        FROM turno t
        JOIN usuario v ON t.veterinario_id = v.id
        WHERE t.veterinaria_id = :idFiltro
        AND DATE(t.fecha_hora) BETWEEN DATE(:fechaInicio) AND DATE(:fechaFin)
        GROUP BY v.id, v.nombre, v.apellido
        ORDER BY v.nombre, porcentaje DESC;
    """, nativeQuery = true)
    List<PorcentajeTurnosAtendAceptReserPorVeterinario> obtenerPorcentajePorVeterinario(
        @Param("idFiltro") Long idFiltro,
        @Param("fechaInicio") Date fechaInicio,
        @Param("fechaFin") Date fechaFin,
        @Param("estados") String estados
    );
}
