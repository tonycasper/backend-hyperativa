package com.hyperativa.challenge.repository;

import com.hyperativa.challenge.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByEncryptedNumber(String encryptedNumber);
}