package com.hugo.api.service;

import com.hugo.api.dto.request.EmpleadoDTORequest;
import com.hugo.api.dto.response.EmpleadoDTOResponse;

import java.util.List;

public interface EmpleadoService {

    EmpleadoDTOResponse agregarEmpleado(EmpleadoDTORequest empleadoDTO);
    List<EmpleadoDTOResponse> obtenerEmpleados();
    EmpleadoDTOResponse obtenerEmpleadoPorId(Long id);
    EmpleadoDTOResponse actualizarEmpleado(Long id, EmpleadoDTORequest empleadoDTO);
    void eliminarEmpleado(Long id);

}
