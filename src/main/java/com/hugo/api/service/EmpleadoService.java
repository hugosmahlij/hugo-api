package com.hugo.api.service;

import com.hugo.api.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    Empleado agregarEmpleado(Empleado empleado);
    List<Empleado> obtenerEmpleados();
    Empleado obtenerEmpleadoPorId(Long id);
    void elimiarEmpleado(Long id);

}
