package com.uts.sistemarecomendacion.recomendador_docentes.repository.postgres;

import com.uts.sistemarecomendacion.recomendador_docentes.model.postgres.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByTeacherId(Integer teacherId);
    List<Activity> findByTeacherIdAndSemester(Integer teacherId, String semester);
}