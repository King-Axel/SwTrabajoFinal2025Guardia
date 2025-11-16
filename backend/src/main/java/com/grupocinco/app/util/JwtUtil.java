package com.grupocinco.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    // PENDIENTE: Usar variables de entorno
    private static final String SECRET = "!-%$#@pan-con-jamon-recien-armado-hace-2-minutos-y-medio-esto-tiene-que-ser-largo-por-seguridad";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 8; // El token dura 8h

    // Genera token, guarda en subject identificador del usuario y en claim datos adicionales como rol
    public static String generateToken(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_MS))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Parsea el token, obteniendo datos del Claim en el payload
    public static <T> T extractClaim(String token, Function<Claims, T> extractor) {
        try {
            Claims clams = extractAllClaims(token);
            return extractor.apply(clams);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    // Parsea el token, obteniendo datos de identificacion del payload
    public static String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
