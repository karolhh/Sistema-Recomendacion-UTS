package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteDetalleDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.service.GestionDocentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gestion")
public class GestionDocentesController {

    @Autowired
    private GestionDocentesService gestionDocentesService;

    @GetMapping("/docentes")
    public ResponseEntity<List<DocenteDetalleDTO>> listarDocentes() {
        return ResponseEntity.ok(gestionDocentesService.listarDocentesConProyectos());
    }
}