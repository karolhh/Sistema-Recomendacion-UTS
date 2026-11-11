package com.uts.sistemarecomendacion.recomendador_docentes.dto;

import lombok.Data;

@Data
public class ProyectoAsignadoDTO {
    private Long trabajoId;
    private String titulo;
    private String etapa;
    private String rol;
}