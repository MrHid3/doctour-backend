package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.*;
import jakarta.servlet.ServletException;
import org.antlr.v4.runtime.Token;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String messageToError(String message, String errorClass){
        if(message.isEmpty()){
            return errorClass +
                    "EXCEPTION";
        }else{
            return errorClass +
                    "_" +
                    message;
        }
    }

    @ExceptionHandler(AppUserException.class)
    public ResponseEntity<String> handleAppUserException(AppUserException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "USER"));
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<String> handleEmailException(EmailException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "EMAIL"));
    }

    @ExceptionHandler(GenderException.class)
    public ResponseEntity<String> handleGenderException(GenderException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "GENDER"));
    }

    @ExceptionHandler(LocationException.class)
    public ResponseEntity<String> handleLocationException(LocationException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "LOCATION"));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<String> handlePasswordException(PasswordException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PASSWORD"));
    }

    @ExceptionHandler(PrivilegeException.class)
    public ResponseEntity<String> handlePrivilegeException(PrivilegeException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PRIVILEGE"));
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<String> handleRoleException(RoleException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "ROLE"));
    }
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> handleTokenException(TokenException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "TOKEN"));
    }
    @ExceptionHandler(UsernameException.class)
    public ResponseEntity<String> handleUsernameException(UsernameException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "USERNAME"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PASSWORD"));
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<String> handleInternalException(BadCredentialsException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "INTERNAL_ERROR"));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(SpecializationException.class)
    public ResponseEntity<String> handleSpecializationException(SpecializationException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "SPECIALIZATION"));
    }

    @ExceptionHandler(AvailabilityException.class)
    public ResponseEntity<String> handleAvailabilityException(AvailabilityException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "AVAILABILITY"));
    }

    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<String> handleAppointmentException(AppointmentException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "APPOINTMENT"));
    }
}
