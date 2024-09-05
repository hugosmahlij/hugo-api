package com.hugo.api.controller;


import com.hugo.api.dto.EmpleadoDTORequest;
import com.hugo.api.dto.EmpleadoDTOResponse;
import com.hugo.api.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    //Aca van los endpoints REST para manejar empleados.

    @Autowired
    EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoDTOResponse> agregarEmpleado(@Valid @RequestBody EmpleadoDTORequest empleadoDTO) {
        EmpleadoDTOResponse respuesta = empleadoService.agregarEmpleado(empleadoDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTOResponse>> obtenerEmpleados() {
        List<EmpleadoDTOResponse> empleados = empleadoService.obtenerEmpleados();
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTOResponse> obtenerEmpleadoPorId(@PathVariable Long id) {
        EmpleadoDTOResponse empleadoId = empleadoService.obtenerEmpleadoPorId(id);
        return new ResponseEntity<>(empleadoId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
