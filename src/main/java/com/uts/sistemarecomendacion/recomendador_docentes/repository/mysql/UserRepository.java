package com.uts.sistemarecomendacion.recomendador_docentes.repository.mysql;
import com.uts.sistemarecomendacion.recomendador_docentes.model.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {}