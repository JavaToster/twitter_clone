package com.example.twitter_clone.util.exceptions;

import com.example.twitter_clone.DTO.security.ErrorMessageDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> exceptionHandle(AuthValidationException exc){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(400, exc.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> exceptionHandle(AuthException exc){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(404, exc.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> exceptionHandle(IllegalAccessException exc){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(404, exc.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> exceptionHandle(EntityNotFoundException exc){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(404, exc.getMessage());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }
}
