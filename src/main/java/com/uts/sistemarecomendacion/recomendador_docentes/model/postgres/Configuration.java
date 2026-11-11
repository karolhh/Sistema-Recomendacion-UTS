package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "configuration")
@Data
public class Configuration {
    @Id private Integer id;
    private String semester;
    private LocalDate startDate;
    private LocalDate endDate;
    private String information;
    private Integer campusId;
    private String investigacion;
    private String extension;
    private String oaca;
    private String oda;
    private String title;
    private BigDecimal tcHours;
    private BigDecimal mtHours;
}