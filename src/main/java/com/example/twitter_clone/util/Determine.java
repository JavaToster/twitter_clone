package com.example.twitter_clone.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Determine {
    public boolean isEmail(String email){
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email);
    }

    public boolean isPhone(String phone){
        return Pattern.matches("^\\+?\\d{10,15}$", phone);
    }
}
