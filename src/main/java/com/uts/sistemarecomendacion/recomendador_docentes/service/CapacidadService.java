package com.uts.sistemarecomendacion.recomendador_docentes.service;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteCapacidadDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Teacher;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.AsignacionRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.TrabajoGradoRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapacidadService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    public List<DocenteCapacidadDTO> calcularCapacidadTodos() {
        List<Teacher> docentes = teacherRepository.findAll();
        List<DocenteCapacidadDTO> resultado = new ArrayList<>();

        // 1. CALCULAR LA DEMANDA (Total de proyectos * 2)
        long totalTrabajos = trabajoGradoRepository.count();
        long demandaTotal = totalTrabajos * 2; 
        
        // 2. CALCULAR LA OFERTA
        int totalDocentes = docentes.size();
        
        // 3. REGLA DINÁMICA (Solo sube créditos si hay MUCHOS trabajos)
        int factorAjuste = 0;
        if (totalDocentes > 0 && (demandaTotal / totalDocentes) > 5) {
            factorAjuste = (int) ((demandaTotal / totalDocentes) - 5);
        }

        for (Teacher docente : docentes) {
            DocenteCapacidadDTO dto = new DocenteCapacidadDTO();
            dto.setId(docente.getId());
            dto.setNombre(docente.getFirstName() + " " + docente.getLastName());
            
            
            String tipoOriginal = docente.getEmploymentType() != null ? docente.getEmploymentType().trim() : "";
            
            
            if (tipoOriginal.equalsIgnoreCase("N/A") || tipoOriginal.equalsIgnoreCase("n/a") || tipoOriginal.isEmpty()) {
                tipoOriginal = "Cátedra";
            }
            
            dto.setVinculacion(tipoOriginal); 

            // 4. ASIGNAR TOPE BASE
            int topeBase = 1; // Cátedra 
            String tipo = tipoOriginal.toLowerCase();
            
            if (tipo.contains("tiempo completo")) {
                topeBase = 5;
            } else if (tipo.contains("medio tiempo")) {
                topeBase = 3;
            } else if (tipo.contains("cátedra")) {
                topeBase = 1;
            }
            
            // Aplicamos ajuste solo a los de planta
            if (topeBase > 1) {
                topeBase += factorAjuste;
            }
            
            dto.setCreditosBase(topeBase);

            // 5. PROYECTOS YA ASIGNADOS
            long proyectosAsignados = asignacionRepository.countByDocenteId(Long.valueOf(docente.getId()));
            dto.setCreditosGastados(proyectosAsignados);

            // 6. CRÉDITOS DISPONIBLES
            double creditosDisponibles = topeBase - proyectosAsignados;
            dto.setCreditosDisponibles(creditosDisponibles);

            // 7. DISPONIBILIDAD
            dto.setPuedeSerDirector(creditosDisponibles > 0);
            dto.setPuedeSerEvaluador(creditosDisponibles > 0);

            if (creditosDisponibles > 0) {
                resultado.add(dto);
            }
        }

        return resultado;
    }
}