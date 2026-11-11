package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {}