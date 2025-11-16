package com.example.AE4_Seguridad.servicio;

import com.example.AE4_Seguridad.modelo.Usuario;
import com.example.AE4_Seguridad.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No encontrado: " + username));


        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole()));
        return new User(u.getUsername(), u.getPassword(), u.isEnabled(),
                true, true, true, authorities);
    }
}
