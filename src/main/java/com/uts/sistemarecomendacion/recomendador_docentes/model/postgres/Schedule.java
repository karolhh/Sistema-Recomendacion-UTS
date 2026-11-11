package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {
    @Id private Integer id;
    private String semester;
    private String name;
    private String classification;
    private String day;
    private Integer teacherId;
    private Integer moment;
    private Integer activityId;
}