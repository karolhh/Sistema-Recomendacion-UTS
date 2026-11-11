package com.uts.sistemarecomendacion.recomendador_docentes.service;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.LoginRequest;
import com.uts.sistemarecomendacion.recomendador_docentes.dto.RegisterRequest;
import com.uts.sistemarecomendacion.recomendador_docentes.model.Usuario;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para registrar un usuario
    public Usuario registrar(RegisterRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        // Encriptamos la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        return usuarioRepository.save(usuario);
    }

    // Método para login (por ahora valida que exista y la contraseña sea correcta)
    public boolean login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara la contraseña enviada con la encriptada en la BD
            return passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        }
        return false;
    }
}