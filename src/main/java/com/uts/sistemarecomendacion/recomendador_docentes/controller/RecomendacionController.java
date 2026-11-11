package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.service.RecomendacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recomendacion")
public class RecomendacionController {

    @Autowired
    private RecomendacionService recomendacionService;

    @GetMapping("/trabajo/{id}")
    public ResponseEntity<?> recomendarPorTrabajo(@PathVariable Long id) {
        List<Map<String, Object>> recomendaciones = recomendacionService.recomendarDocentes(id);
        
        // Si la lista está vacía, el trabajo no existe o no hay docentes
        if (recomendaciones == null || recomendaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el trabajo de grado con ID: " + id);
        }
        
        return ResponseEntity.ok(recomendaciones);
    }
}