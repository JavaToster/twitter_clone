package com.example.twitter_clone.DTO.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {
    private int code;
    private String message;
}
