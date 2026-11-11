package com.uts.sistemarecomendacion.recomendador_docentes.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "trabajos_grado")
@Data
public class TrabajoGrado {
    @Id 
    private Long id;
    
    @Column(name = "periodo_academico") 
    private String periodoAcademico;
    
    @Column(name = "codigo_tg") 
    private String codigoTg;
    
    private String nivel;
    private String estudiante;
    private String correo;
    private String documento;
    private String celular;
    private String modalidad;
    
    @Column(columnDefinition = "TEXT")
    private String titulo;
    
    private String director;
    private String evaluador;
    private String autores;
    
    @Column(name = "inicio_tg") 
    private Date inicioTg;
    
    @Column(name = "aprobacion_propuesta") 
    private Date aprobacionPropuesta;
    
    @Column(name = "final_tg") 
    private Date finalTg;
    
    @Column(name = "created_at") 
    private Date createdAt;
    
    @Column(name = "updated_at") 
    private Date updatedAt;
    
    @Column(name = "deleted_at") 
    private Date deletedAt;

    private String etapa;
}