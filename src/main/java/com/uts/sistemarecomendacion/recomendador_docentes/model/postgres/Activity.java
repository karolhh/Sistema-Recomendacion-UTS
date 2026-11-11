package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "activity")
@Data
public class Activity {
    @Id private Integer id;
    private String semester;
    private String name;
    private String mission;
    private String convention;
    private String description;
    private String groupName;
    private BigDecimal hours;
    private String responsible;
    
    // AGREGA ESTA LÍNEA:
    @Column(columnDefinition = "jsonb")
    private String product;
    
    private Integer teacherId;
    private String consolidated;
}