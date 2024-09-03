package com.hugo.api.service.impl;

import com.hugo.api.entity.Empleado;
import com.hugo.api.repository.EmpleadoRepository;
import com.hugo.api.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado agregarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public void elimiarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
