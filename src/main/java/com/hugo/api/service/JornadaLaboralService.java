package com.hugo.api.service;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;

import java.time.LocalDate;
import java.util.List;


public interface JornadaLaboralService {
    JornadaLaboralDTOResponse crearJornadaLaboral(JornadaLaboralDTORequest jornadaLaboralDTO);

    List<JornadaLaboralDTOResponse> obtenerJornadas(LocalDate fechaDesde, LocalDate fechaHasta, Integer nroDocumento);
}
