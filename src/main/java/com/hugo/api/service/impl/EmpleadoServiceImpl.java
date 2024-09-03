package com.hugo.api.service.impl;

import com.hugo.api.dto.EmpleadoDTORequest;
import com.hugo.api.dto.EmpleadoDTOResponse;
import com.hugo.api.entity.Empleado;
import com.hugo.api.exception.RecursoNoEncontradoException;
import com.hugo.api.repository.EmpleadoRepository;
import com.hugo.api.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Override
    public EmpleadoDTOResponse agregarEmpleado(EmpleadoDTORequest empleadoDTO) {
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
                empleadoGuardado.getFechaIngreso());
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
                        empleado.getFechaIngreso()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTOResponse obtenerEmpleadoPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con id: " + id));

        return new EmpleadoDTOResponse(
                empleado.getId(),
                empleado.getNroDocumento(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail(),
                empleado.getFechaNacimiento(),
                empleado.getFechaIngreso()
        );
    }

    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
