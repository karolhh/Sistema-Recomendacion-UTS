package com.uts.sistemarecomendacion.recomendador_docentes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Clave secreta para firmar el token (debe ser larga)
    private static final String SECRET_KEY = "esta_es_una_clave_secreta_muy_larga_para_el_proyecto_uts_2026_ok";
    
    // Tiempo de expiración (24 horas = 86400000 milisegundos)
    private static final long EXPIRATION_TIME = 86400000; 

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Genera el token para un usuario
    public String generateToken(String username, String rol) {
        return Jwts.builder()
                .claim("rol", rol) // Guardamos el rol dentro del token
                .subject(username) // <-- Cambiado: setSubject() ahora es subject()
                .issuedAt(new Date(System.currentTimeMillis())) // <-- Cambiado: setIssuedAt() ahora es issuedAt()
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // <-- Cambiado: setExpiration() ahora es expiration()
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida si el token es correcto y no ha expirado
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser() // <-- Cambiado: parserBuilder() ahora es parser()
                .verifyWith((javax.crypto.SecretKey) getSignInKey()) // <-- Cambiado: setSigningKey() ahora es verifyWith()
                .build()
                .parseSignedClaims(token); // <-- Cambiado: parseClaimsJws() ahora es parseSignedClaims()
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrae el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae el rol del token
    public String extractRol(String token) {
        return extractClaim(token, claims -> claims.get("rol", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSignInKey()) // <-- Cambiado también aquí
                .build()
                .parseSignedClaims(token) // <-- Cambiado
                .getPayload(); // <-- Cambiado: getBody() ahora es getPayload()
        return claimsResolver.apply(claims);
    }
}