//package com.hyperativa.challenge.controller;
//
//import com.hyperativa.challenge.dto.AuthRequest;
//import com.hyperativa.challenge.util.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthControllerTest {
//
//    private JwtUtil jwtUtil;
//    private AuthenticationManager authenticationManager;
//    private AuthController authController;
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = mock(JwtUtil.class);
//        authenticationManager = mock(AuthenticationManager.class);
//        authController = new AuthController(jwtUtil, authenticationManager);
//    }
//
//    @Test
//    void testAuthenticateReturnsTokenWhenCredentialsAreValid() {
//        // Arrange
//        AuthRequest authRequest = new AuthRequest("user", "password");
//        Authentication authentication = mock(Authentication.class);
//
//        when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())))
//                .thenReturn(authentication);
//
//        when(jwtUtil.generateToken(authRequest.getUsername())).thenReturn("mocked-jwt-token");
//
//        // Act
//        String token = authController.authenticate(authRequest);
//
//        // Assert
//        assertEquals("mocked-jwt-token", token);
//        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
//        verify(jwtUtil, times(1)).generateToken(authRequest.getUsername());
//    }
//
//    @Test
//    void testAuthenticateThrowsExceptionWhenCredentialsAreInvalid() {
//        // Arrange
//        AuthRequest authRequest = new AuthRequest("user", "wrong-password");
//
//        when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())))
//                .thenThrow(new RuntimeException("Invalid credentials"));
//
//        // Act & Assert
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.authenticate(authRequest));
//        assertEquals("Invalid credentials", exception.getMessage());
//        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
//        verify(jwtUtil, never()).generateToken(anyString());
//    }
//}