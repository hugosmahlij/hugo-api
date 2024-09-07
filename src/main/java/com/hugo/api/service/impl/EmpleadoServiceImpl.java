package com.hugo.api.service.impl;

import com.hugo.api.dto.request.EmpleadoDTORequest;
import com.hugo.api.dto.response.EmpleadoDTOResponse;
import com.hugo.api.entity.Empleado;
import com.hugo.api.exception.IdNoEncontradoException;
import com.hugo.api.repository.EmpleadoRepository;
import com.hugo.api.repository.JornadaLaboralRepository;
import com.hugo.api.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private JornadaLaboralRepository jornadaLaboralRepository;


    @Override
    public EmpleadoDTOResponse agregarEmpleado(EmpleadoDTORequest empleadoDTO) {
        LocalDate fechaDeHoy = LocalDate.now();

        // Verificar que la fecha de nacimiento no es mayo a la del día
        if (empleadoDTO.getFechaNacimiento().isAfter(fechaDeHoy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser mayor a la fecha de hoy.");
        }

        // Verificacion de la edad minima del empleado (18 años)
        if(empleadoDTO.getFechaNacimiento().plusYears(18).isAfter(fechaDeHoy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La edad del empleado no puede ser menor a 18 años.");
        }

        // Verificacion de que la fecha de ingreso no sea mayor a la del día
        if (empleadoDTO.getFechaIngreso().isAfter(fechaDeHoy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de ingreso no puede ser posterior al día hoy.");
        }

        // El email lo verifico por parametro ingresado por DTORequest con la validacion de Spring

        // Verificar que el nombre solo tenga letras
        if (!empleadoDTO.getNombre().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten letras en el nombre.");
        }

        // Verfiicar que el apellido solo tenga letras
        if (!empleadoDTO.getApellido().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se permiten letras en el apellido.");
        }

        // Valido que no haya otro empleado con el mismo nro documento
        if (empleadoRepository.existsByNroDocumento(empleadoDTO.getNroDocumento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con el documento ingresado.");
        }

        // Verfico que el email no coincida con otro ya creado
        if (empleadoRepository.existsByEmail(empleadoDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con el email ingresado.");
        }

        // Creacion del empleado (si pasa validaciones)
        Empleado empleado = new Empleado();
        empleado.setNroDocumento(empleadoDTO.getNroDocumento());
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setFechaIngreso(empleadoDTO.getFechaIngreso());

        Empleado empleadoGuardado = empleadoRepository.save(empleado);

        return new EmpleadoDTOResponse(
                empleadoGuardado.getId(),
                empleadoGuardado.getNroDocumento(),
                empleadoGuardado.getNombre(),
                empleadoGuardado.getApellido(),
                empleadoGuardado.getEmail(),
                empleadoGuardado.getFechaNacimiento(),
                empleadoGuardado.getFechaIngreso(),
                empleadoGuardado.getFechaCreacion());
    }

    @Override
    public List<EmpleadoDTOResponse> obtenerEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(empleado -> new EmpleadoDTOResponse(
                        empleado.getId(),
                        empleado.getNroDocumento(),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getEmail(),
                        empleado.getFechaNacimiento(),
                        empleado.getFechaIngreso(),
                        empleado.getFechaCreacion()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTOResponse obtenerEmpleadoPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new IdNoEncontradoException("No se encontró el empleado con Id: " + id));

        return new EmpleadoDTOResponse(
                empleado.getId(),
                empleado.getNroDocumento(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail(),
                empleado.getFechaNacimiento(),
                empleado.getFechaIngreso(),
                empleado.getFechaCreacion()
        );
    }

    @Override
    public EmpleadoDTOResponse actualizarEmpleado(Long id, EmpleadoDTORequest empleadoDTO) {
        Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(() -> new IdNoEncontradoException("No se encontró el empleado con Id: " + id));

        // Verificaciones de sobreescritura de campos
        if (empleadoRepository.existsByNroDocumento((empleadoDTO.getNroDocumento())) &&
        !empleadoExistente.getNroDocumento().equals(empleadoDTO.getNroDocumento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con el documento ingresado.");
        }

        if (empleadoRepository.existsByEmail(empleadoDTO.getEmail()) &&
        !empleadoExistente.getEmail().equals(empleadoDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un empleado con el email ingresado.");
        }

        // Actualizar campos
        empleadoExistente.setNroDocumento(empleadoDTO.getNroDocumento());
        empleadoExistente.setNombre(empleadoDTO.getNombre());
        empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoDTO.getFechaIngreso());

        empleadoRepository.save(empleadoExistente);

        return new EmpleadoDTOResponse(
                empleadoExistente.getId(),
                empleadoExistente.getNroDocumento(),
                empleadoExistente.getNombre(),
                empleadoExistente.getApellido(),
                empleadoExistente.getEmail(),
                empleadoExistente.getFechaNacimiento(),
                empleadoExistente.getFechaIngreso(),
                empleadoExistente.getFechaCreacion()
        );
    }

    @Override
    public void eliminarEmpleado(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el empleado con Id: " + empleadoId));

        if (jornadaLaboralRepository.existsByEmpleadoId(empleadoId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No es posible eliminar un empleado con jornadas asociadas.");
        }

        empleadoRepository.delete(empleado);
    }
}
