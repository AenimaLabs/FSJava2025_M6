package com.example.AE4_Seguridad.config;

import com.example.AE4_Seguridad.modelo.Usuario;
import com.example.AE4_Seguridad.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                Usuario a = new Usuario();
                a.setUsername("admin");
                a.setPassword(encoder.encode("admin123"));
                a.setRole("ADMIN");
                a.setEnabled(true);
                repo.save(a);
            }

            if (repo.findByUsername("user").isEmpty()) {
                Usuario u = new Usuario();
                u.setUsername("user");
                u.setPassword(encoder.encode("user123"));
                u.setRole("USER");
                u.setEnabled(true);
                repo.save(u);
            }
        };
    }
}
