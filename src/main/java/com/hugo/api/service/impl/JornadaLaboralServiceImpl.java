package com.hugo.api.service.impl;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;
import com.hugo.api.entity.ConceptoLaboral;
import com.hugo.api.entity.Empleado;
import com.hugo.api.entity.JornadaLaboral;
import com.hugo.api.repository.ConceptoLaboralRepository;
import com.hugo.api.repository.EmpleadoRepository;
import com.hugo.api.repository.JornadaLaboralRepository;
import com.hugo.api.service.JornadaLaboralService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadaLaboralServiceImpl implements JornadaLaboralService {

    private final JornadaLaboralRepository jornadaLaboralRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ConceptoLaboralRepository conceptoLaboralRepository;

    public JornadaLaboralServiceImpl(JornadaLaboralRepository jornadaLaboralRepository, EmpleadoRepository empleadoRepository, ConceptoLaboralRepository conceptoLaboralRepository) {
        this.jornadaLaboralRepository = jornadaLaboralRepository;
        this.empleadoRepository = empleadoRepository;
        this.conceptoLaboralRepository = conceptoLaboralRepository;
    }


    @Override
    public JornadaLaboralDTOResponse crearJornadaLaboral(JornadaLaboralDTORequest jornadaLaboralDTO) {
        Empleado empleado = empleadoRepository.findById(jornadaLaboralDTO.getIdEmpleado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el empleado ingresado."));

        ConceptoLaboral conceptoLaboral = conceptoLaboralRepository.findById(jornadaLaboralDTO.getIdConcepto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el concepto ingresado."));


        // Regla 1 - Validacion de rango de horas por concepto laboral
        Integer horasTrabajadas = jornadaLaboralDTO.getHorasTrabajadas();
        if (horasTrabajadas != null) {
            Integer hsMinimo = conceptoLaboral.getHsMinimo();
            Integer hsMaximo = conceptoLaboral.getHsMaximo();

            if (hsMinimo != null && hsMaximo != null) {
                if (horasTrabajadas < conceptoLaboral.getHsMinimo() || horasTrabajadas > conceptoLaboral.getHsMaximo()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rango de horas que se puede cargar para este concepto es de " + conceptoLaboral.getHsMinimo() + " - " + conceptoLaboral.getHsMaximo());
                }
            }
        }

        // Regla 2 - Validacion de suma de horas en el día por empleado
        LocalDate fecha = jornadaLaboralDTO.getFecha();
        List<JornadaLaboral> jornadasPorDia = jornadaLaboralRepository.findByEmpleadoAndFecha(empleado, fecha);

        int horasTotales = jornadasPorDia.stream()
                .mapToInt(JornadaLaboral::getHorasTrabajadas)
                .sum();

        if (horasTotales + jornadaLaboralDTO.getHorasTrabajadas() > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un empleado no puede cargar más de 14 horas trabajadas por día.");
        }

        // Regla 3 - Horas semanales por empleado (lunes a viernes)
        LocalDate inicioSemana = fecha.with(DayOfWeek.MONDAY);
        LocalDate finSemana = fecha.with(DayOfWeek.FRIDAY);

        List<JornadaLaboral> jornadaEmpleadoSemana = jornadaLaboralRepository.findByEmpleadoAndFechaBetween(empleado, inicioSemana, finSemana);

        // Filtrar las jornadas de lunes a viernes
        int totalHorasSemanales = jornadaEmpleadoSemana.stream()
                .filter(jornada -> jornada.getFecha().getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue()
                        && jornada.getFecha().getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue())
                .mapToInt(JornadaLaboral::getHorasTrabajadas)
                .sum();

        // Verificar si el empleado supera las 52 horas semanales
        if (totalHorasSemanales + jornadaLaboralDTO.getHorasTrabajadas() > 52) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ingresado supera las 52 horas semanales.");
        }

        // Regla 4 - No más de 190 horas mensuales por empleado
        LocalDate inicioMes = fecha.withDayOfMonth(1);
        LocalDate finMes = fecha.withDayOfMonth(fecha.lengthOfMonth());

        List<JornadaLaboral> jornadasEmpleadoMes = jornadaLaboralRepository.findByEmpleadoAndFechaBetween(empleado, inicioMes,finMes);

        int totalHorasMensuales = jornadasEmpleadoMes.stream()
                .mapToInt(JornadaLaboral::getHorasTrabajadas)
                .sum();

        if (totalHorasSemanales + jornadaLaboralDTO.getHorasTrabajadas() > 190) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ingresado supera las 190 horas mensuales.");
        }

        // Regla 5 - Verificar que no tenga dia libre en la fecha
        boolean tieneDiaLibre = jornadaLaboralRepository.findByEmpleadoAndFechaAndConceptoLaboralId(empleado, fecha, 3).isPresent();
        System.out.println("Concepto Laboral: " + conceptoLaboral.getId());

        if (tieneDiaLibre) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ingresado ya cuenta con un día libre en esa fecha.");
        }

        // Regla 6 - No más de 3 turnos extra por semana
        long turnosExtraSemana = jornadaEmpleadoSemana.stream()
                .filter(jornada -> jornada.getConceptoLaboral().getId() == 2)
                .count();

        if (turnosExtraSemana >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ingresado ya cuenta con 3 turnos extra esta semana.");
        }

        // Regla 7 - No más de 5 turnos normales por semana
        long turnosNormalesSemana = jornadaEmpleadoSemana.stream()
                .filter(jornada -> jornada.getConceptoLaboral().getId() == 1)
                .count();

        if (turnosNormalesSemana >= 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ingresado ya cuenta con 5 turnos normales esta semana.");
        }

        // Regla 8 - Solo 2 dias libres por semana
        long diasLibresSemana = jornadaEmpleadoSemana.stream()
                .filter(jornada -> jornada.getConceptoLaboral().getId() == 3)
                .map(JornadaLaboral::getFecha)
                .distinct()
                .count();

        if (diasLibresSemana >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado no cuenta con más días libres esta semana.");
        }

        // Regla 9 - Solo 5 días libres al mes
        long diasLibresMes = jornadasEmpleadoMes.stream()
                .filter(jornada -> jornada.getConceptoLaboral().getId() == 3)
                .map(JornadaLaboral::getFecha)
                .distinct()
                .count();

        if (diasLibresMes >= 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado no cuenta con más días libres este mes.");
        }

        // Regla 10 - No más de 2 empleados por jornada
        long empleadosPorJornada = jornadaLaboralRepository.countByConceptoLaboralAndFecha(conceptoLaboral,fecha);

        if (empleadosPorJornada >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existen 2 empleados registrados para este concepto en la fecha ingresada.");
        }

        // Regla 11 - No es posible cargar a un empleado con dos mismos conceptos de jornadas en el mismo día
        boolean jornadaExistente = jornadaLaboralRepository.findByEmpleadoAndFechaAndConceptoLaboral(empleado, fecha, conceptoLaboral.getId()).isPresent();

        if (jornadaExistente) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }

        // CREACION DE JORNADA LABORAL
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConceptoLaboral(conceptoLaboral);
        jornadaLaboral.setFecha(jornadaLaboralDTO.getFecha());
        jornadaLaboral.setHorasTrabajadas(jornadaLaboralDTO.getHorasTrabajadas());

        jornadaLaboralRepository.save(jornadaLaboral);

        JornadaLaboralDTOResponse jornadaResponse = new JornadaLaboralDTOResponse();
        jornadaResponse.setId(jornadaLaboral.getId());
        jornadaResponse.setNroDocumento(empleado.getNroDocumento());
        jornadaResponse.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        jornadaResponse.setFecha(jornadaLaboral.getFecha());
        jornadaResponse.setConcepto(conceptoLaboral.getNombre());
        jornadaResponse.setHorasTrabajadas(jornadaLaboral.getHorasTrabajadas());

        return jornadaResponse;
    }

    @Override
    public List<JornadaLaboralDTOResponse> obtenerJornadas(LocalDate fechaDesde, LocalDate fechaHasta, Integer nroDocumento) {
        // Si NO se proporcionan fechas, se utilizan valores por defecto
        LocalDate fechaInicio = (fechaDesde != null) ? fechaDesde : LocalDate.of(2000,1,1);
        LocalDate fechaFin = (fechaHasta != null) ? fechaHasta : LocalDate.now();

        List<JornadaLaboral> jornadas;

        if (nroDocumento != null) {
            jornadas = jornadaLaboralRepository.findByNroDocumentoAndFechaBetween(nroDocumento, fechaInicio, fechaFin);
        } else {
            jornadas = jornadaLaboralRepository.findByFechaBetween(fechaInicio, fechaFin);
        }

        return jornadas.stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    private JornadaLaboralDTOResponse convertirAResponse(JornadaLaboral jornada) {
        JornadaLaboralDTOResponse jornadaDto = new JornadaLaboralDTOResponse();
        jornadaDto.setId(jornada.getId());
        jornadaDto.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        jornadaDto.setNombreCompleto(jornada.getEmpleado().getNombre() + " " + jornada.getEmpleado().getApellido());
        jornadaDto.setFecha(jornada.getFecha());
        jornadaDto.setConcepto(jornada.getConceptoLaboral().getNombre());
        jornadaDto.setHorasTrabajadas(jornada.getHorasTrabajadas());

        return jornadaDto;
    }
}
