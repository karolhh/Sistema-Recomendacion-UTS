package com.uts.sistemarecomendacion.recomendador_docentes.model.mysql;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "actas")
@Data
public class Acta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private Integer numero;
    private LocalDate fecha;
    private String descripcion;
    private Long proyectoId;
}