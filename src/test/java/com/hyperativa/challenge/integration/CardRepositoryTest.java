package com.hyperativa.challenge.integration;

import com.hyperativa.challenge.entity.Card;
import com.hyperativa.challenge.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class CardRepositoryTest {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CardRepository cardRepository;

    @Test
    void testSaveAndFetchCard() {
        // Create a new card
        Card card = new Card();
        card.setId(UUID.randomUUID());
        card.setCardNumber("4111111111111111");
        card.setCardHolderName("Jane Doe");
        card.setExpirationDate(LocalDate.of(2026, 1, 31));
        card.setSecurityCode("456");
        card.setEncryptedNumber("EncryptedNumber");

        // Save the card
        Card savedCard = cardRepository.save(card);

        // Fetch the card
        Card fetchedCard = cardRepository.findById(savedCard.getId()).orElse(null);

        // Assert the saved and fetched data
        assertThat(fetchedCard).isNotNull();
        assertThat(fetchedCard.getCardNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedCard.getEncryptedNumber()).isEqualTo("EncryptedNumber");
    }
}