package com.hugo.api.controller;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;
import com.hugo.api.service.JornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {

    @Autowired
    JornadaLaboralService jornadaLaboralService;

    @PostMapping
    public ResponseEntity<JornadaLaboralDTOResponse> registrarJornada(@RequestBody @Valid JornadaLaboralDTORequest jornadaLaboralDTO) {
        JornadaLaboralDTOResponse jornadaCreada = jornadaLaboralService.crearJornadaLaboral(jornadaLaboralDTO);
        return new ResponseEntity<>(jornadaCreada, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JornadaLaboralDTOResponse>> obtenerJornadas(
            @RequestParam(required = false) LocalDate fechaDesde,
            @RequestParam(required = false) LocalDate fechaHasta,
            @RequestParam(required = false) Integer nroDocumento) {

        List<JornadaLaboralDTOResponse> jornadas = jornadaLaboralService.obtenerJornadas(fechaDesde,fechaHasta,nroDocumento);
        return ResponseEntity.ok(jornadas);
    }
}
