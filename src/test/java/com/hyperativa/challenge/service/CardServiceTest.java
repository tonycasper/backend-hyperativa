//package com.hyperativa.challenge.service;
//
//import com.hyperativa.challenge.entity.Card;
//import com.hyperativa.challenge.repository.CardRepository;
//import com.hyperativa.challenge.util.EncryptionUtil;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CardServiceTest {
//    private final CardRepository cardRepository = Mockito.mock(CardRepository.class);
//    private final EncryptionUtil encryptionUtil = Mockito.mock(EncryptionUtil.class);
//    private final CardService cardService = new CardService(cardRepository);
//
//    @Test
//    void testSaveCardEncryptsData() {
//        Card card = new Card();
//        card.setCardNumber("4111111111111111");
//        card.setCardHolderName("John Doe");
//        card.setExpirationDate(LocalDate.of(2025, 12, 31));
//        card.setSecurityCode("123");
//
//        // Set mock behavior
//        when(encryptionUtil.encrypt("4111111111111111")).thenReturn("EncryptedValue");
//        when(cardRepository.save(any(Card.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Call the service
//        Card savedCard = cardService.saveCard(card);
//
//        // Verify the behavior
//        verify(encryptionUtil, times(1)).encrypt("4111111111111111");
//        verify(cardRepository, times(1)).save(card);
//        assertEquals("EncryptedValue", savedCard.getEncryptedNumber());
//    }
//}