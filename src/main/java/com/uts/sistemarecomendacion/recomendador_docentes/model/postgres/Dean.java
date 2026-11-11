package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dean")
@Data
public class Dean {
    @Id private Integer id;
    private String document;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String faculty;
    private Integer campusId;
    private String role;
}