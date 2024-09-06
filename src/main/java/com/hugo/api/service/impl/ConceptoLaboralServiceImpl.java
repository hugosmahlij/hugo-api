package com.hugo.api.service.impl;

import com.hugo.api.dto.response.ConceptoLaboralDTOResponse;
import com.hugo.api.entity.ConceptoLaboral;
import com.hugo.api.repository.ConceptoLaboralRepository;
import com.hugo.api.service.ConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConceptoLaboralServiceImpl implements ConceptoLaboralService {

    @Autowired
    public ConceptoLaboralRepository conceptoLaboralRepository;

    @Override
    public List<ConceptoLaboralDTOResponse> obtenerConceptosLaborales(Integer id, String nombre) {
        List<ConceptoLaboral> conceptos;

        if (id != null && nombre != null) {
            // Busca por id y luego filtra por nombre
            conceptos = conceptoLaboralRepository.findById(id).stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        } else if (id != null) {
            // Busca por id
            conceptos = conceptoLaboralRepository.findById(id)
                    .map(List::of)
                    .orElse(List.of()); // Devuelve lista vacia si no está
        } else if (nombre != null) {
            // Busca por nombre
            conceptos = conceptoLaboralRepository.findByNombreContainingIgnoreCase(nombre);
        } else {
            // Retorna todos
            conceptos = conceptoLaboralRepository.findAll();
        }

        // Convierte conceptos a DTOresponse
        return conceptos.stream()
                .map(c -> new ConceptoLaboralDTOResponse(
                        c.getId(),
                        c.getNombre(),
                        c.getHsMinimo(),
                        c.getHsMaximo(),
                        c.getLaborable()
                ))
                .collect(Collectors.toList());
    }
}
