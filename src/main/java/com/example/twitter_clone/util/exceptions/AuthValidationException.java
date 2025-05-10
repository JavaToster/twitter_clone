package com.example.twitter_clone.util.exceptions;


public class AuthValidationException extends RuntimeException{
    public AuthValidationException(String msg){
        super(msg);
    }
}
