package com.hyperativa.challenge.integration;

import com.hyperativa.challenge.dto.AuthRequest;
import com.hyperativa.challenge.util.JwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.crypto.SecretKey;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtil jwtUtil;

    private SecretKey key;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key
    }

    @Test
    void testLoginEndpoint() {
        AuthRequest authRequest = new AuthRequest("user", "password");

        Response response = given()
                .contentType("application/json")
                .body(authRequest)
                .when()
                .post("/auth/login");

        response.then()
                .assertThat()
                .statusCode(200)
                .body(notNullValue());

        String token = response.getBody().asString();

        assert token != null;
        assert !token.isEmpty();
        assert jwtUtil.validateToken(token, "user");
    }
}
