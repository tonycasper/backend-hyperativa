//package com.hyperativa.challenge.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hyperativa.challenge.entity.Card;
//import com.hyperativa.challenge.repository.CardRepository;
//import com.hyperativa.challenge.util.EncryptionUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.*;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class CardControllerIntegrationTest {
//
//    @Container
//    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
//            .withDatabaseName("test_db")
//            .withUsername("test")
//            .withPassword("test");
//
//    @LocalServerPort
//    private int port;
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mysqlContainer::getUsername);
//        registry.add("spring.datasource.password", mysqlContainer::getPassword);
//    }
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private CardRepository cardRepository; // Repositório para validação direta do banco
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testAddCard() {
//        // Número de cartão a ser enviado
//        String cardNumber = "4111111111111111";
//
//        // Faz a requisição para adicionar um cartão
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> request = new HttpEntity<>(cardNumber, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(
//                "http://localhost:" + port + "/cards/addCard",
//                request,
//                String.class
//        );
//
//        // Verifica status da resposta
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo("Card saved successfully");
//
//        // Confirma que o cartão foi salvo no banco de dados, comparando o número criptografado
//        String encryptedCardNumber = EncryptionUtil.encrypt(cardNumber);
//        Optional<Card> savedCard = cardRepository.findByEncryptedNumber(encryptedCardNumber);
//        assertThat(savedCard).isPresent();
//    }
//
//    @Test
//    void testAddCardsFromFile() {
//        // Arquivo com números de cartão
//        ClassPathResource resource = new ClassPathResource("test-cards.txt"); // test-cards.txt deve estar na pasta resources
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        HttpEntity<ClassPathResource> request = new HttpEntity<>(resource, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:" + port + "/cards/addCardsFromFile",
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//
//        // Verifica status da resposta
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo("Cards saved successfully");
//
//        // Verifica se os cartões no arquivo foram criptografados e salvos no banco
//        assertThat(cardRepository.count()).isGreaterThan(0); // Certifica-se de que cartões foram salvos
//    }
//
//    @Test
//    void testFindCardFound() {
//        // Adiciona um cartão diretamente no banco de dados (cartão criptografado)
//        String cardNumber = "4111111111111111";
//        String encryptedNumber = EncryptionUtil.encrypt(cardNumber);
//        Card card = new Card();
//        card.setEncryptedNumber(encryptedNumber);
//        cardRepository.save(card);
//
//        // Busca o cartão pelo endpoint
//        ResponseEntity<Long> response = restTemplate.getForEntity(
//                "http://localhost:" + port + "/cards/findCard/" + cardNumber,
//                Long.class
//        );
//
//        // Verifica se o cartão foi encontrado e o ID retornado
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(card.getId());
//    }
//
//    @Test
//    void testFindCardNotFound() {
//        // Número de cartão que não existe
//        String cardNumber = "1234567890123456";
//
//        // Busca o cartão pelo endpoint
//        ResponseEntity<Void> response = restTemplate.getForEntity(
//                "http://localhost:" + port + "/cards/findCard/" + cardNumber,
//                Void.class
//        );
//
//        // Verifica se o cartão não foi encontrado
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//}