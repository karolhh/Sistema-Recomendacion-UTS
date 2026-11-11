package com.uts.sistemarecomendacion.recomendador_docentes.dto;

import lombok.Data;
import java.util.List;

@Data
public class DocenteDetalleDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String vinculacion;
    private String programa;
    private int totalProyectos;
    private int totalComoDirector;
    private int totalComoEvaluador;
    private double creditosDisponibles;
    private List<ProyectoAsignadoDTO> proyectos;
}