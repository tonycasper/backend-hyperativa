package com.hyperativa.challenge.dto;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}