//package com.hyperativa.challenge.controller;
//
//import com.hyperativa.challenge.dto.AuthRequest;
//import com.hyperativa.challenge.util.JwtUtil;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.Matchers.not;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class AuthControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @BeforeEach
//    void setUp() {
//        // Configura a URL base para os testes RestAssured
//        RestAssured.port = port;
//        RestAssured.baseURI = "http://localhost";
//    }
//
//    @Test
//    void testLoginEndpoint() {
//        // Monta o objeto AuthRequest
//        AuthRequest authRequest = new AuthRequest("admin", "password");
//
//        // Executa a requisição para o endpoint de login
//        Response response = given()
//                .contentType("application/json") // Define o tipo JSON para a requisição
//                .body(authRequest) // Adiciona o corpo da requisição
//                .when()
//                .post("/auth/login"); // Endpoint do login
//
//        // Validações do teste
//        response.then()
//                .assertThat()
//                .statusCode(200) // Verifica se o status HTTP retornado é 200
//                .body(notNullValue()); // O corpo da resposta não pode ser nulo
//
//        // Captura o token da resposta
//        String token = response.getBody().asString();
//
//        // Valida o token JWT
//        assert token != null;
//        assert !token.isEmpty();
//        assert jwtUtil.validateToken(token, "user");
//    }
//}