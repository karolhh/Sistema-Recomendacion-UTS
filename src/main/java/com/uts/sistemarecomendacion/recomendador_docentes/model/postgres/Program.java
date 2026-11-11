package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "program")
@Data
public class Program {
    @Id private Integer id;
    private String name;
    private Integer campusId;
    private String faculty;
}