package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {
    @Id private Integer id;
    private String document;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String card;
    private String faculty;
    private String campus;
    private String employmentType;
    private String rank;
    private String undergraduate;
    private String specialization;
    private String master;
    private String doctorate;
    private Integer programId;
    private Boolean isActive;
    private String programName;
    private String role;
}