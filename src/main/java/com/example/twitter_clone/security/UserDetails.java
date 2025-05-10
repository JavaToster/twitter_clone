package com.example.twitter_clone.security;

import com.example.twitter_clone.models.user.User;
import com.example.twitter_clone.util.enums.AuthenticationType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;
    private final AuthenticationType type;

    public UserDetails(User user, AuthenticationType type){
        this.user = user;
        this.type = type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (type == AuthenticationType.EMAIL){
            return user.getEmail();
        }else{
            return user.getPhone();
        }
    }
}
