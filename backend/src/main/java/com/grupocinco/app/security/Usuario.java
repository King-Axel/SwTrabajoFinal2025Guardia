package com.grupocinco.app.security;

import com.grupocinco.app.util.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Usuario implements UserDetails {
    private final String username;
    private final String passwordHash;
    private final Rol rol;

    public Usuario(String username, String passwordHash, Rol rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority>  getAuthorities() {
        return rol.getGrantedAuthorities();
    }

    @Override public String getPassword() { return passwordHash; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
