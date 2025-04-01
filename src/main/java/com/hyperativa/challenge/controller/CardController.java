package com.hyperativa.challenge.controller;

import com.hyperativa.challenge.entity.Card;
import com.hyperativa.challenge.service.CardService;
import com.hyperativa.challenge.util.EncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
@PreAuthorize("hasRole('USER')")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/addCard")
    public ResponseEntity<String> addCard(@RequestBody String cardNumber) {
        String encrypted = EncryptionUtil.encrypt(cardNumber);
        cardService.saveEncryptedCard(encrypted);
        return ResponseEntity.ok("Card saved successfully");
    }

    @PostMapping("/addCardsFromFile")
    public ResponseEntity<String> addCardsFromFile(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String encrypted = EncryptionUtil.encrypt(line);
                cardService.saveEncryptedCard(encrypted);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
        }
        return ResponseEntity.ok("Cards saved successfully");
    }

    @GetMapping("/findCard/{cardNumber}")
    public ResponseEntity<?> findCard(@PathVariable String cardNumber) {
        String encrypted = EncryptionUtil.encrypt(cardNumber);
        Optional<Card> found = cardService.findByEncryptedNumber(encrypted);
        return found.isPresent() ? ResponseEntity.ok(found.get().getId()) : ResponseEntity.notFound().build();
    }
}