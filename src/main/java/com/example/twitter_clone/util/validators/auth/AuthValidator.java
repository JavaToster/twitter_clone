package com.example.twitter_clone.util.validators.auth;

import com.example.twitter_clone.DTO.auth.AuthDTO;
import com.example.twitter_clone.DTO.auth.RegisterDTO;
import com.example.twitter_clone.util.Determine;
import com.example.twitter_clone.util.exceptions.AuthValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final Determine determine;

    public void registerValidate(RegisterDTO registerDTO, BindingResult errors){
        if (errors.hasErrors()){
            String msg = Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage();
            throw new AuthValidationException(msg);
        }

        if (registerDTO.getEmail() == null || registerDTO.getEmail().isBlank()){
            if (registerDTO.getPhone() == null || registerDTO.getPhone().isBlank()){
                throw new AuthValidationException("You should enter email or phone for register");
            }
        }
    }

    public void authValidate(AuthDTO authDTO, BindingResult errors){
        if (errors.hasErrors()){
            StringBuilder msg = new StringBuilder();
            for(ObjectError error: errors.getAllErrors()){
                msg.append(error.getDefaultMessage());
                msg.append(";");
            }

            throw new AuthValidationException(msg.toString());
        }

        if ( !determine.isEmail((authDTO.getLogin()) )&& !determine.isPhone(authDTO.getLogin()) ){
            throw new AuthValidationException("email/phone format is not correct, please enter another email address/phone number");
        }
    }
}
