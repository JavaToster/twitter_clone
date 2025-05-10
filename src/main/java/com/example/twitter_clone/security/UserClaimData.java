package com.example.twitter_clone.security;

import com.example.twitter_clone.util.enums.AuthenticationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class UserClaimData {
    private String claim;
    private AuthenticationType type;
}
