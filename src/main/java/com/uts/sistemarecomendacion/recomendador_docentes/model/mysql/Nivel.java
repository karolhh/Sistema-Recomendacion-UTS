package com.uts.sistemarecomendacion.recomendador_docentes.model.mysql;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "niveles")
@Data
public class Nivel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nombre;
    private String descripcion;
}