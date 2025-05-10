package com.example.twitter_clone.DTO.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDTO {
    @NotBlank(message = "login should be not empty")
    private String login;
    @NotBlank(message = "password should be not empty")
    private String password;
}
