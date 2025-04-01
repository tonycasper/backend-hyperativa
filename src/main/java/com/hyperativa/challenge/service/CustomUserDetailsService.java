package com.hyperativa.challenge.service;

import com.hyperativa.challenge.entity.UserEntity;
import com.hyperativa.challenge.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo nome de usuário
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Converte UserEntity em UserDetails
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // A senha deve estar criptografada no banco
                .roles(user.getRole()) // Define os papéis do usuário
                .build();
    }
}