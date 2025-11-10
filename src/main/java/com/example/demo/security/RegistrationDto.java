package com.example.demo.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationDto {
    @NotBlank @Size(min = 3, max = 64)
    private String username;

    @NotBlank @Size(min = 4, max = 64)
    private String password;

    @NotBlank @Size(min = 4, max = 64)
    private String confirm;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirm() { return confirm; }
    public void setConfirm(String confirm) { this.confirm = confirm; }
}
