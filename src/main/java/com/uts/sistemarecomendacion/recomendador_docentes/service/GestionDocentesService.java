package com.uts.sistemarecomendacion.recomendador_docentes.service;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteCapacidadDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.dto.DocenteDetalleDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.dto.ProyectoAsignadoDTO;
import com.uts.sistemarecomendacion.recomendador_docentes.model.Asignacion;
import com.uts.sistemarecomendacion.recomendador_docentes.model.TrabajoGrado;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Teacher;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.AsignacionRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.TrabajoGradoRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GestionDocentesService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private TrabajoGradoRepository trabajoGradoRepository;

    @Autowired
    private CapacidadService capacidadService;

    public List<DocenteDetalleDTO> listarDocentesConProyectos() {
        List<Teacher> docentes = teacherRepository.findAll();
        List<DocenteDetalleDTO> resultado = new ArrayList<>();

        // 1. CALCULAR LOS CRÉDITOS UNA SOLA VEZ AL PRINCIPIO
        List<DocenteCapacidadDTO> capacidades = capacidadService.calcularCapacidadTodos();
        Map<Integer, Double> creditosMap = capacidades.stream()
                .collect(Collectors.toMap(DocenteCapacidadDTO::getId, DocenteCapacidadDTO::getCreditosDisponibles));

        // 2. RECORRER LOS DOCENTES
        for (Teacher docente : docentes) {
            DocenteDetalleDTO dto = new DocenteDetalleDTO();
            dto.setId(docente.getId());
            dto.setNombre(docente.getFirstName() + " " + docente.getLastName());
            dto.setEmail(docente.getEmail());
            dto.setVinculacion(docente.getEmploymentType());
            dto.setPrograma(docente.getProgramName());

            // 3. ASIGNACIONES DEL DOCENTE
            List<Asignacion> asignaciones = asignacionRepository.findByDocenteId(Long.valueOf(docente.getId()));
            
            int totalDirector = 0;
            int totalEvaluador = 0;
            List<ProyectoAsignadoDTO> proyectos = new ArrayList<>();

            for (Asignacion asignacion : asignaciones) {
                ProyectoAsignadoDTO proyectoDto = new ProyectoAsignadoDTO();
                proyectoDto.setTrabajoId(asignacion.getTrabajoGradoId());
                proyectoDto.setRol(asignacion.getRol());

                // 4. BUSCAR EL TRABAJO (pero solo si es necesario)
                Optional<TrabajoGrado> trabajoOpt = trabajoGradoRepository.findById(asignacion.getTrabajoGradoId());
                if (trabajoOpt.isPresent()) {
                    TrabajoGrado trabajo = trabajoOpt.get();
                    proyectoDto.setTitulo(trabajo.getTitulo());
                    proyectoDto.setEtapa(trabajo.getEtapa());
                }

                if ("DIRECTOR".equalsIgnoreCase(asignacion.getRol())) {
                    totalDirector++;
                } else if ("EVALUADOR".equalsIgnoreCase(asignacion.getRol())) {
                    totalEvaluador++;
                }

                proyectos.add(proyectoDto);
            }

            dto.setProyectos(proyectos);
            dto.setTotalProyectos(asignaciones.size());
            dto.setTotalComoDirector(totalDirector);
            dto.setTotalComoEvaluador(totalEvaluador);
            
            // 5. USAR EL MAPA DE CRÉDITOS
            dto.setCreditosDisponibles(creditosMap.getOrDefault(docente.getId(), 0.0));

            resultado.add(dto);
        }

        return resultado;
    }
}