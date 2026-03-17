package com.doctour.doctourbe.exception;

public class UsernameTakenException extends Exception {
    public UsernameTakenException(){}

    public UsernameTakenException(String message){
        super(message);
    }
}
