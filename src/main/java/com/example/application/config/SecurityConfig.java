package com.example.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF deaktivieren (nur für APIs, nicht für Web-Apps mit Sessions!)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/registrieren").permitAll()  // Registrierung ohne Auth erlauben
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable()) // Kein Login-Formular
                .httpBasic(httpBasic -> httpBasic.disable()); // Kein Basic-Auth

        return http.build();
    }

}
