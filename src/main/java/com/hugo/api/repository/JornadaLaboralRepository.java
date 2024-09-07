package com.hugo.api.repository;

import com.hugo.api.entity.ConceptoLaboral;
import com.hugo.api.entity.Empleado;
import com.hugo.api.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JornadaLaboralRepository extends JpaRepository<JornadaLaboral, Integer> {
    List<JornadaLaboral> findByEmpleadoAndFecha(Empleado empleado, LocalDate date);

    List<JornadaLaboral> findByEmpleadoAndFechaBetween(Empleado empleado, LocalDate inicioSemana, LocalDate finSemana);

    Optional<JornadaLaboral> findByEmpleadoAndFechaAndConceptoLaboralId(Empleado empleado, LocalDate fecha, Integer idConcepto);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j WHERE j.conceptoLaboral = :conceptoLaboral AND j.fecha = :fecha")
    long countByConceptoLaboralAndFecha(@Param("conceptoLaboral") ConceptoLaboral conceptoLaboral, @Param("fecha") LocalDate fecha);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.empleado = :empleado AND j.fecha = :fecha AND j.conceptoLaboral.id = :conceptoId")
    Optional<JornadaLaboral> findByEmpleadoAndFechaAndConceptoLaboral(@Param("empleado") Empleado empleado,@Param("fecha") LocalDate fecha,@Param("conceptoId") Integer conceptoId);

    List<JornadaLaboral> findByFechaBetween(LocalDate fechaDesde, LocalDate fechaHasta);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.empleado.nroDocumento = :nroDocumento AND j.fecha BETWEEN :fechaDesde AND :fechaHasta")
    List<JornadaLaboral> findByNroDocumentoAndFechaBetween(@Param("nroDocumento") Integer nroDocumento, @Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta);

    boolean existsByEmpleadoId(Long empleadoId);
}
