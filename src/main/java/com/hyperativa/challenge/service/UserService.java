package com.hyperativa.challenge.service;

import com.hyperativa.challenge.entity.UserEntity;
import com.hyperativa.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
}