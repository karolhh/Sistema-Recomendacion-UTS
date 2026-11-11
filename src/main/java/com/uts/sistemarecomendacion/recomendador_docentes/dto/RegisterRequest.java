package com.uts.sistemarecomendacion.recomendador_docentes.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String rol;
}