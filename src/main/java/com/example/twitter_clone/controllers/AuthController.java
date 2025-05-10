package com.example.twitter_clone.controllers;

import com.example.twitter_clone.DTO.auth.AuthDTO;
import com.example.twitter_clone.DTO.auth.AuthTokenDTO;
import com.example.twitter_clone.DTO.auth.RegisterDTO;
import com.example.twitter_clone.models.user.User;
import com.example.twitter_clone.services.user.UserService;
import com.example.twitter_clone.util.Convertor;
import com.example.twitter_clone.util.validators.auth.AuthValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthValidator authValidator;
    private final Convertor convertor;

    @PostMapping("/sing_up")
    public ResponseEntity<HttpStatus> singUp(@RequestBody @Valid RegisterDTO registerDTO, BindingResult errors){
        authValidator.registerValidate(registerDTO, errors);

        User user = convertor.convertToUser(registerDTO);
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sing_in")
    public ResponseEntity<AuthTokenDTO> singIn(@RequestBody @Valid AuthDTO authDTO, BindingResult errors){
        authValidator.authValidate(authDTO, errors);

        String jwt = userService.sing_in(authDTO);
        return new ResponseEntity<>(new AuthTokenDTO(jwt), HttpStatus.OK);
    }
}
