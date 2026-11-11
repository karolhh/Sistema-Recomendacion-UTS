package com.uts.sistemarecomendacion.recomendador_docentes.repository;

import com.uts.sistemarecomendacion.recomendador_docentes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Esto le dice a Spring que busque un usuario por su username
    Optional<Usuario> findByUsername(String username);
}