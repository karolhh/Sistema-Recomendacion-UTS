package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.repository.TrabajoGradoRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mysql")
public class MySqlApiController {

    @Autowired private NivelRepository nivelRepo;
    @Autowired private ModalidadRepository modalidadRepo;
    @Autowired private LineaInvestigacionRepository lineaRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private TrabajoGradoRepository trabajoRepo;
    @Autowired private ActaRepository actaRepo;

    @GetMapping("/niveles") public ResponseEntity<?> getNiveles() { return ResponseEntity.ok(nivelRepo.findAll()); }
    @GetMapping("/modalidades") public ResponseEntity<?> getModalidades() { return ResponseEntity.ok(modalidadRepo.findAll()); }
    @GetMapping("/lineas") public ResponseEntity<?> getLineas() { return ResponseEntity.ok(lineaRepo.findAll()); }
    @GetMapping("/users") public ResponseEntity<?> getUsers() { return ResponseEntity.ok(userRepo.findAll()); }
    @GetMapping("/trabajos") public ResponseEntity<?> getTrabajos() { return ResponseEntity.ok(trabajoRepo.findAll()); }
    @GetMapping("/actas") public ResponseEntity<?> getActas() { return ResponseEntity.ok(actaRepo.findAll()); }
}