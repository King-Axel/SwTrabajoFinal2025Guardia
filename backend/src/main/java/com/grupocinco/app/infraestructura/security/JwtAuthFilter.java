package com.grupocinco.app.infraestructura.security;

import com.grupocinco.app.infraestructura.util.JwtUtil;
import com.grupocinco.app.infraestructura.util.Rol;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtUtil.isTokenValid(token)) {
                String subject = jwtUtil.extractSubject(token);
                String rolTk = jwtUtil.extractClaim(token, claims -> (String) claims.get("rol"));

                Rol rol = Rol.desdeString(rolTk);

                var authorities = new ArrayList<GrantedAuthority>();

                authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.name()));
                authorities.addAll(rol.getGrantedAuthorities());

                var auth =  new UsernamePasswordAuthenticationToken(subject, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }
}
