package com.hugo.api.controller;

import com.hugo.api.dto.response.ConceptoLaboralDTOResponse;
import com.hugo.api.entity.ConceptoLaboral;
import com.hugo.api.service.ConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concepto-laboral")
public class ConceptoLaboralController {

    @Autowired
    private ConceptoLaboralService conceptoLaboralService;

    @GetMapping
    public ResponseEntity<List<ConceptoLaboralDTOResponse>> obtenerConceptosLaborales(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nombre", required = false) String nombre) {

        List<ConceptoLaboralDTOResponse> conceptos = conceptoLaboralService.obtenerConceptosLaborales(id, nombre);

        return new ResponseEntity<>(conceptos, HttpStatus.OK);
    }


}
