package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Dean;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DeanRepository extends JpaRepository<Dean, Integer> {}