package com.hugo.api.service;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;


public interface JornadaLaboralService {
    JornadaLaboralDTOResponse crearJornadaLaboral(JornadaLaboralDTORequest jornadaLaboralDTO);
}
