package com.hugo.api.service;

import com.hugo.api.dto.response.ConceptoLaboralDTOResponse;
import com.hugo.api.entity.ConceptoLaboral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConceptoLaboralService {
    List<ConceptoLaboralDTOResponse> obtenerConceptosLaborales(Integer id, String nombre);
}
