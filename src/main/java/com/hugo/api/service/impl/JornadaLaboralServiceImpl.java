package com.hugo.api.service.impl;

import com.hugo.api.dto.request.JornadaLaboralDTORequest;
import com.hugo.api.dto.response.JornadaLaboralDTOResponse;
import com.hugo.api.entity.ConceptoLaboral;
import com.hugo.api.entity.Empleado;
import com.hugo.api.entity.JornadaLaboral;
import com.hugo.api.repository.ConceptoLaboralRepository;
import com.hugo.api.repository.EmpleadoRepository;
import com.hugo.api.repository.JornadaLaboralRepository;
import com.hugo.api.service.JornadaLaboralService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JornadaLaboralServiceImpl implements JornadaLaboralService {

    private final JornadaLaboralRepository jornadaLaboralRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ConceptoLaboralRepository conceptoLaboralRepository;

    public JornadaLaboralServiceImpl(JornadaLaboralRepository jornadaLaboralRepository, EmpleadoRepository empleadoRepository, ConceptoLaboralRepository conceptoLaboralRepository) {
        this.jornadaLaboralRepository = jornadaLaboralRepository;
        this.empleadoRepository = empleadoRepository;
        this.conceptoLaboralRepository = conceptoLaboralRepository;
    }


    @Override
    public JornadaLaboralDTOResponse crearJornadaLaboral(JornadaLaboralDTORequest jornadaLaboralDTO) {
        Empleado empleado = empleadoRepository.findById(jornadaLaboralDTO.getIdEmpleado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el empleado ingresado."));

        ConceptoLaboral conceptoLaboral = conceptoLaboralRepository.findById(jornadaLaboralDTO.getIdConcepto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el concepto ingresado."));

        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConceptoLaboral(conceptoLaboral);
        jornadaLaboral.setFecha(jornadaLaboralDTO.getFecha());
        jornadaLaboral.setHorasTrabajadas(jornadaLaboralDTO.getHorasTrabajadas());

        jornadaLaboralRepository.save(jornadaLaboral);

        JornadaLaboralDTOResponse jornadaResponse = new JornadaLaboralDTOResponse();
        jornadaResponse.setId(jornadaLaboral.getId());
        jornadaResponse.setNroDocumento(empleado.getNroDocumento());
        jornadaResponse.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        jornadaResponse.setFecha(jornadaLaboral.getFecha());
        jornadaResponse.setConcepto(conceptoLaboral.getNombre());
        jornadaResponse.setHorasTrabajadas(jornadaLaboral.getHorasTrabajadas());

        return jornadaResponse;
    }
}
