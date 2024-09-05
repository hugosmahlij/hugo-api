package com.hugo.api.service.impl;

import com.hugo.api.dto.EmpleadoDTORequest;
import com.hugo.api.dto.EmpleadoDTOResponse;
import com.hugo.api.entity.Empleado;
import com.hugo.api.exception.IdNoEncontradoException;
import com.hugo.api.repository.EmpleadoRepository;
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
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
