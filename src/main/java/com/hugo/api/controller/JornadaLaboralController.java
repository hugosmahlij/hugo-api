package com.hugo.api.controller;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;
import com.hugo.api.service.JornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
