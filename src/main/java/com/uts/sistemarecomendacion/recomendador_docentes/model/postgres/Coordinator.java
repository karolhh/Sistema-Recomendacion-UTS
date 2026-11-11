package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coordinator")
@Data
public class Coordinator {
    @Id private Integer id;
    private String document;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Integer programId;
}