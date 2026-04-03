package com.servicios.sppp.back_end_sppp.dto;

public class CredencialesRequest {
    private String email;
    private String password;

    public CredencialesRequest() {
    }

    public CredencialesRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}