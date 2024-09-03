package com.hugo.api.service;

import com.hugo.api.dto.EmpleadoDTORequest;
import com.hugo.api.dto.EmpleadoDTOResponse;
import com.hugo.api.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    EmpleadoDTOResponse agregarEmpleado(EmpleadoDTORequest empleadoDTO);
    List<EmpleadoDTOResponse> obtenerEmpleados();
    EmpleadoDTOResponse obtenerEmpleadoPorId(Long id);
    void eliminarEmpleado(Long id);

}
