package com.doctour.doctourbe.exception;

public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(){}

    public InvalidPasswordException(String message){
        super(message);
    }
}
