package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CoordinatorRepository extends JpaRepository<Coordinator, Integer> {}