package com.uts.sistemarecomendacion.recomendador_docentes.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "asignaciones")
@Data
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trabajo_grado_id")
    private Long trabajoGradoId;

    @Column(name = "docente_id")
    private Long docenteId;

    private String rol; // DIRECTOR o EVALUADOR

    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;
}