package com.hyperativa.challenge.repository;

import com.hyperativa.challenge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Método para buscar um usuário pelo nome de usuário
    Optional<UserEntity> findByUsername(String username);

}