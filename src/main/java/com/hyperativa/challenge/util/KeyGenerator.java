package com.hyperativa.challenge.util;

import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        byte[] secretKey = "MySecretKeyValueForJWT".getBytes();
        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey);
        System.out.println("Base64 Encoded Key: " + base64EncodedKey);
    }
}