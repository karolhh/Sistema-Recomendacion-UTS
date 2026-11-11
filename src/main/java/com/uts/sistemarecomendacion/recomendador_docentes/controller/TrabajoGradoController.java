package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.model.Asignacion;
import com.uts.sistemarecomendacion.recomendador_docentes.model.TrabajoGrado;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.AsignacionRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.TrabajoGradoRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Teacher;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/trabajos")
public class TrabajoGradoController {

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/con-asignaciones")
    public ResponseEntity<?> getTrabajosConAsignaciones() {
        List<TrabajoGrado> trabajos = trabajoGradoRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (TrabajoGrado trabajo : trabajos) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", trabajo.getId());
            map.put("titulo", trabajo.getTitulo());
            map.put("estudiante", trabajo.getEstudiante());
            map.put("etapa", trabajo.getEtapa());

            // Buscar asignaciones de este trabajo
            List<Asignacion> asignaciones = asignacionRepository.findByTrabajoGradoId(trabajo.getId());
            
            String nombreDirector = null;
            String nombreEvaluador = null;

            for (Asignacion asig : asignaciones) {
                Optional<Teacher> docenteOpt = teacherRepository.findById(asig.getDocenteId().intValue());
                if (docenteOpt.isPresent()) {
                    Teacher docente = docenteOpt.get();
                    String nombreCompleto = docente.getFirstName() + " " + docente.getLastName();
                    if ("DIRECTOR".equalsIgnoreCase(asig.getRol())) {
                        nombreDirector = nombreCompleto;
                    } else if ("EVALUADOR".equalsIgnoreCase(asig.getRol())) {
                        nombreEvaluador = nombreCompleto;
                    }
                }
            }

            map.put("directorAsignado", nombreDirector);
            map.put("evaluadorAsignado", nombreEvaluador);
            map.put("estaAsignado", nombreDirector != null && nombreEvaluador != null);

            resultado.add(map);
        }

        return ResponseEntity.ok(resultado);
    }
}