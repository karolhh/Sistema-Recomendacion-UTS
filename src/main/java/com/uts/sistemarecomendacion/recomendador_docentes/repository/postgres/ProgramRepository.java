package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Program;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProgramRepository extends JpaRepository<Program, Integer> {}