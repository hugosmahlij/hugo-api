package com.hugo.api.controller;


import com.hugo.api.dto.request.EmpleadoDTORequest;
import com.hugo.api.dto.response.EmpleadoDTOResponse;
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

    @GetMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTOResponse> obtenerEmpleadoPorId(@PathVariable Long empleadoId) {
        EmpleadoDTOResponse idEmpleado = empleadoService.obtenerEmpleadoPorId(empleadoId);
        return new ResponseEntity<>(idEmpleado, HttpStatus.OK);
    }

    @PutMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTOResponse> actualizarEmpleado(@PathVariable Long empleadoId, @RequestBody @Valid EmpleadoDTORequest empleadoDTO) {
        EmpleadoDTOResponse empleadoActualizado = empleadoService.actualizarEmpleado(empleadoId,empleadoDTO);
        return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long empleadoId) {
        empleadoService.eliminarEmpleado(empleadoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
