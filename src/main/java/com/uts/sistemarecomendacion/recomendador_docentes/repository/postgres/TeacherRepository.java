package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;
import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {}