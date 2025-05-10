package com.example.twitter_clone.DTO.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String phone;
    @NotBlank(message = "Password should be not empty!")
    private String password;
    private String firstname;
    private String lastname;
    private String bio;
}
