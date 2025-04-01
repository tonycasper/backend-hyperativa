package com.hyperativa.challenge.config;

import com.hyperativa.challenge.entity.UserEntity;
import com.hyperativa.challenge.repository.UserRepository;
import com.hyperativa.challenge.service.CustomUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService; // O Spring injeta automaticamente o bean
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Um encoder para senhas (recomendada para segurança)
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Forma atualizada para desabilitar o CSRF
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/login").permitAll() // Permite acesso público ao endpoint
                                .anyRequest().authenticated() // Exige autenticação para outras requisições
                );
        return http.build();
    }

    @Bean
    public CommandLineRunner createTestUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(new UserEntity("user", passwordEncoder.encode("password"), "ROLE_USER"));
            }
        };
    }
}