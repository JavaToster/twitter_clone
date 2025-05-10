package com.example.twitter_clone.util;

import com.example.twitter_clone.DTO.auth.RegisterDTO;
import com.example.twitter_clone.models.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Convertor {
    private final ModelMapper modelMapper;
    public User convertToUser(RegisterDTO registerDTO){
        return modelMapper.map(registerDTO, User.class);
    }
}
