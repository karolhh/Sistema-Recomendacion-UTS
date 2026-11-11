package com.uts.sistemarecomendacion.recomendador_docentes.repository.mysql;
import com.uts.sistemarecomendacion.recomendador_docentes.model.mysql.Acta;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ActaRepository extends JpaRepository<Acta, Long> {}