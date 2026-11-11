package com.uts.sistemarecomendacion.recomendador_docentes.config;

import com.uts.sistemarecomendacion.recomendador_docentes.service.SqlExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SqlExtractorService sqlExtractorService;

    @Override
    public void run(String... args) throws Exception {
        
    	System.out.println("Sistema iniciado. Los datos ya estan en Neon.");
    }
}