package com.uts.sistemarecomendacion.recomendador_docentes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SqlExtractorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void extraerYGuardar(String nombreArchivo) {
        try {
            ClassPathResource resource = new ClassPathResource(nombreArchivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            
            String contenido = sb.toString();
            
            // Expresión regular para encontrar INSERT INTO ... VALUES (...);
            // Busca desde INSERT INTO hasta el punto y coma final
            Pattern pattern = Pattern.compile("INSERT INTO\\s+\\S+\\s*\\([^)]*\\)\\s*VALUES\\s*\\(.*?\\);", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(contenido);
            
            int count = 0;
            int errores = 0;
            
            System.out.println("Iniciando extracción de: " + nombreArchivo);

            while (matcher.find()) {
                String sql = matcher.group().trim();
                
                // Limpiar comillas invertidas
                sql = sql.replace("`", "");
                sql = sql.replace("public.", "");
                
                // Redirigir historico a trabajos_grado
                if (sql.toUpperCase().contains("INSERT INTO HISTORICO")) {
                    sql = sql.replace("INSERT INTO historico", "INSERT INTO trabajos_grado");
                    sql = sql.replace("INSERT INTO HISTORICO", "INSERT INTO trabajos_grado");
                }
                
                // Quitar el punto y coma final
                if (sql.endsWith(";")) {
                    sql = sql.substring(0, sql.length() - 1);
                }
                
                try {
                    jdbcTemplate.execute(sql);
                    count++;
                    if (count % 100 == 0) {
                        System.out.println("Progreso: " + count + " registros insertados.");
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    String msg = e.getMessage() != null ? e.getMessage() : "";
                    if (!msg.contains("duplicate key")) {
                        errores++;
                    }
                }
            }
            
            System.out.println("¡Se insertaron " + count + " registros del archivo " + nombreArchivo + "!");
            System.out.println("Errores no fatales: " + errores);

        } catch (Exception e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}