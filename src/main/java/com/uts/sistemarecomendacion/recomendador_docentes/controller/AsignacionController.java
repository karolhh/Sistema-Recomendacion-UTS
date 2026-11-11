package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.model.Asignacion;
import com.uts.sistemarecomendacion.recomendador_docentes.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/asignacion")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @PostMapping("/manual")
    public ResponseEntity<?> asignarManual(@RequestBody Map<String, Object> request) {
        try {
            Long trabajoId = Long.valueOf(request.get("trabajoId").toString());
            Long docenteId = Long.valueOf(request.get("docenteId").toString());
            String rol = request.get("rol").toString(); // "DIRECTOR" o "EVALUADOR"

            Asignacion asignacion = asignacionService.asignar(trabajoId, docenteId, rol);

            return ResponseEntity.ok(Map.of(
                "mensaje", "Asignación realizada con éxito",
                "asignacion", asignacion
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al asignar: " + e.getMessage());
        }
    }
}