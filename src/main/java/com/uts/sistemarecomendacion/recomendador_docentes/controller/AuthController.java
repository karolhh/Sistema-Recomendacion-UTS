package com.uts.sistemarecomendacion.recomendador_docentes.controller;

import com.uts.sistemarecomendacion.recomendador_docentes.dto.LoginRequest;
import com.uts.sistemarecomendacion.recomendador_docentes.dto.RegisterRequest;
import com.uts.sistemarecomendacion.recomendador_docentes.model.Usuario;
import com.uts.sistemarecomendacion.recomendador_docentes.repository.UsuarioRepository;
import com.uts.sistemarecomendacion.recomendador_docentes.security.JwtService;
import com.uts.sistemarecomendacion.recomendador_docentes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Usuario usuarioGuardado = usuarioService.registrar(request);
        return ResponseEntity.ok("Usuario " + usuarioGuardado.getUsername() + " registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean exito = usuarioService.login(request);
        
        if (exito) {
            // Si el login es correcto, buscamos al usuario para obtener su rol
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
            if(usuarioOpt.isPresent()){
                Usuario usuario = usuarioOpt.get();
                // Generamos el Token JWT
                String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol());
                return ResponseEntity.ok(token); // Devolvemos el token como respuesta
            }
        }
        return ResponseEntity.badRequest().body("Credenciales incorrectas");
    }
}