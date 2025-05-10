package com.example.twitter_clone.DTO.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserSettingsDTO {
    private DateDTO birthDate;
    private boolean is_private;
    private String email;
    private String phone;
    private String username;
    private String firstname;
    private String lastname;
    private String bio;


}
