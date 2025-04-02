package com.hyperativa.challenge.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperativa.challenge.dto.AuthRequest;
import com.hyperativa.challenge.entity.Card;
import com.hyperativa.challenge.repository.CardRepository;
import com.hyperativa.challenge.util.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CardControllerIntegrationTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String obtainAccessToken(String username, String password) {
        AuthRequest authRequest = new AuthRequest(username, password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/auth/login",
                authRequest,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    @Test
    void testAddCard() {
        String token = obtainAccessToken("user", "password");
        String cardNumber = "4111111111111111";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(cardNumber, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/cards/addCard",
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Card saved successfully");

        String encryptedCardNumber = EncryptionUtil.encrypt(cardNumber);
        Optional<Card> savedCard = cardRepository.findByEncryptedNumber(encryptedCardNumber);
        assertThat(savedCard).isPresent();
    }

    @Test
    void testAddCardsFromFile() {
        ClassPathResource resource = new ClassPathResource("test-cards.txt");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<ClassPathResource> request = new HttpEntity<>(resource, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/cards/addCardsFromFile",
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Cards saved successfully");

        assertThat(cardRepository.count()).isGreaterThan(0);
    }

    @Test
    void testFindCardFound() {
        String cardNumber = "4111111111111111";
        String encryptedNumber = EncryptionUtil.encrypt(cardNumber);
        Card card = new Card();
        card.setEncryptedNumber(encryptedNumber);
        cardRepository.save(card);

        ResponseEntity<Long> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/cards/findCard/" + cardNumber,
                Long.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(card.getId());
    }

    @Test
    void testFindCardNotFound() {
        String cardNumber = "1234567890123456";

        ResponseEntity<Void> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/cards/findCard/" + cardNumber,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
