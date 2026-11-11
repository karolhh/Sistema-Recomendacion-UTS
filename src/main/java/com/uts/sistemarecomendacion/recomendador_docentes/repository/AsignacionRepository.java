package com.uts.sistemarecomendacion.recomendador_docentes.repository;

import com.uts.sistemarecomendacion.recomendador_docentes.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    long countByDocenteId(Long docenteId);
    List<Asignacion> findByDocenteId(Long docenteId);
	List<Asignacion> findByTrabajoGradoId(Long id);
}