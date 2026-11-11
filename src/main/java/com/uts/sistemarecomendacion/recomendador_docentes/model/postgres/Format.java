package com.uts.sistemarecomendacion.recomendador_docentes.model.postgres;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "format")
@Data
public class Format {
    @Id private Integer id;
    private String semester;
    private Boolean isFinish;
    private Integer teacherId;
    
    // AGREGA ESTA LÍNEA (columnDefinition = "TEXT")
    @Column(columnDefinition = "TEXT")
    private String observation;
    
    private Boolean isDeanSigned;
    private Boolean isCoordSigned;
}