package com.hyperativa.challenge.service;

import com.hyperativa.challenge.entity.Card;
import com.hyperativa.challenge.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void saveEncryptedCard(String encryptedNumber) {
        Card card = new Card();
        card.setEncryptedNumber(encryptedNumber);
        cardRepository.save(card);
    }

    public Optional<Card> findByEncryptedNumber(String encryptedNumber) {
        return cardRepository.findByEncryptedNumber(encryptedNumber);
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }
}