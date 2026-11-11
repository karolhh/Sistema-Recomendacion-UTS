package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteCapacidadDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.service.CapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/capacidad")
public class CapacidadController {

    @Autowired
    private CapacidadService capacidadService;

    @GetMapping("/docentes")
    public ResponseEntity<List<DocenteCapacidadDTO>> listarCapacidades() {
        return ResponseEntity.ok(capacidadService.calcularCapacidadTodos());
    }
}