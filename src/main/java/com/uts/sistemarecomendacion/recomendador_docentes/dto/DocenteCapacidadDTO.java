package com.uts.sistemarecomendacion.recomendador_docentes.dto;

import lombok.Data;

@Data
public class DocenteCapacidadDTO {
    private Integer id;
    private String nombre;
    private String vinculacion;
    private double creditosBase;
    private double creditosGastados;
    private double creditosDisponibles;
    private boolean puedeSerDirector;
    private boolean puedeSerEvaluador;
}