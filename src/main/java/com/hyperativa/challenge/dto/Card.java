package com.hyperativa.challenge.dto;

import jakarta.persistence.*;
import java.util.UUID;

//@Data
//@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String encryptedNumber;

    // Getters and setters
}