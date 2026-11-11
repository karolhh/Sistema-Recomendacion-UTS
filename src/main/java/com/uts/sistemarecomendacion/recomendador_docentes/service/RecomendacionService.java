package com.uts.sistemarecomendacion.recomendador_docentes.service;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteCapacidadDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.model.TrabajoGrado;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Teacher;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.TrabajoGradoRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacionService {

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CapacidadService capacidadService;

    public List<Map<String, Object>> recomendarDocentes(Long trabajoId) {
        // 1. Buscar el trabajo de grado por ID
        Optional<TrabajoGrado> trabajoOpt = trabajoGradoRepository.findById(trabajoId);
        if (trabajoOpt.isEmpty()) {
            return Collections.emptyList();
        }
        
        TrabajoGrado trabajo = trabajoOpt.get();
        String tituloTrabajo = trabajo.getTitulo() != null ? trabajo.getTitulo().toLowerCase() : "";
        
        // 2. Obtener solo los docentes con créditos disponibles
        List<DocenteCapacidadDTO> docentesConCreditos = capacidadService.calcularCapacidadTodos();
        
        if (docentesConCreditos.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 3. Obtener todos los docentes de la base de datos
        List<Teacher> todosLosDocentes = teacherRepository.findAll();
        
        // 4. Calcular puntaje para cada docente con créditos
        List<Map<String, Object>> recomendaciones = new ArrayList<>();
        
        for (DocenteCapacidadDTO docenteCapacidad : docentesConCreditos) {
            Optional<Teacher> docenteOpt = todosLosDocentes.stream()
                .filter(d -> d.getId().equals(docenteCapacidad.getId()))
                .findFirst();
            
            if (docenteOpt.isEmpty()) continue;
            
            Teacher docente = docenteOpt.get();
            int puntaje = 0;
            
            String programaDocente = docente.getProgramName() != null ? docente.getProgramName().toLowerCase() : "";
            String facultadDocente = docente.getFaculty() != null ? docente.getFaculty().toLowerCase() : "";
            String masterDocente = docente.getMaster() != null ? docente.getMaster().toLowerCase() : "";
            
            // Puntaje base por ser del programa de Sistemas
            if (programaDocente.contains("sistema") || programaDocente.contains("software")) {
                puntaje += 50;
            }
            
            // Puntaje por coincidencia de palabras
            String[] palabrasTitulo = tituloTrabajo.split("\\s+");
            for (String palabra : palabrasTitulo) {
                if (palabra.length() > 3) {
                    if (programaDocente.contains(palabra)) puntaje += 10;
                    if (facultadDocente.contains(palabra)) puntaje += 5;
                    if (masterDocente.contains(palabra)) puntaje += 8;
                }
            }
            
            // Puntos extra por formación
            if (docente.getMaster() != null && !docente.getMaster().isEmpty()) puntaje += 15;
            if (docente.getDoctorate() != null && !docente.getDoctorate().isEmpty()) puntaje += 20;
            if (docente.getEmploymentType() != null && docente.getEmploymentType().toLowerCase().contains("tiempo completo")) {
                puntaje += 10;
            }
            
            Map<String, Object> recomendacion = new HashMap<>();
            recomendacion.put("id", docente.getId());
            recomendacion.put("nombre", docente.getFirstName() + " " + docente.getLastName());
            recomendacion.put("email", docente.getEmail());
            recomendacion.put("programa", docente.getProgramName());
            recomendacion.put("master", docente.getMaster());
            recomendacion.put("puntaje", puntaje);
            recomendacion.put("creditosDisponibles", docenteCapacidad.getCreditosDisponibles());
            recomendacion.put("vinculacion", docente.getEmploymentType());
            recomendaciones.add(recomendacion);
        }
        
        // 5. Ordenar por puntaje
        List<Map<String, Object>> ordenados = recomendaciones.stream()
                .sorted((a, b) -> Integer.compare((int) b.get("puntaje"), (int) a.get("puntaje")))
                .collect(Collectors.toList());

        // 6. Crear respuesta con Director y Evaluador
        Map<String, Object> respuesta = new HashMap<>();
        
        if (!ordenados.isEmpty()) {
            respuesta.put("director", ordenados.get(0));
            
            if (ordenados.size() > 1) {
                respuesta.put("evaluador", ordenados.get(1));
            } else {
                respuesta.put("evaluador", "No hay un segundo docente disponible con perfil adecuado.");
            }
        }
        
        return Collections.singletonList(respuesta);
    }
}