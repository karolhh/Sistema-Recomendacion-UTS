package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "campus")
@Data
public class Campus {
    @Id private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;
}