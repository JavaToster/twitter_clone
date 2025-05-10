package com.example.twitter_clone.controllers;

import com.example.twitter_clone.DTO.user.UserSettingsDTO;
import com.example.twitter_clone.services.user.UserService;
import com.example.twitter_clone.util.validators.user.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PatchMapping("/{id}/settings/change")
    public ResponseEntity<HttpStatus> changeSettings(@RequestBody  UserSettingsDTO userSettingsDTO, @PathVariable("id") long id) throws IllegalAccessException{
        userService.updateUserSettings(id, userSettingsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
