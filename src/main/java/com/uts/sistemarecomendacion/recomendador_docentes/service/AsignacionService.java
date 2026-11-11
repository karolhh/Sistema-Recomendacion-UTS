package com.uts.sistemarecomendacion.recomendador_docentes.service;

import com.uts.sistemarecomendacion.recomendador_docentes.model.Asignacion;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    public Asignacion asignar(Long trabajoId, Long docenteId, String rol) {
        Asignacion asignacion = new Asignacion();
        asignacion.setTrabajoGradoId(trabajoId);
        asignacion.setDocenteId(docenteId);
        asignacion.setRol(rol.toUpperCase()); // DIRECTOR o EVALUADOR
        asignacion.setFechaAsignacion(LocalDateTime.now());
        return asignacionRepository.save(asignacion);
    }
}