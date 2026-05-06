package com.doctour.doctourbe.service;

import com.doctour.doctourbe.exception.*;
import jakarta.servlet.ServletException;
import org.antlr.v4.runtime.Token;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, String> messageToError(String message, String errorClass){
        HashMap<String, String> errorMap = new HashMap<>();
        if(message.isEmpty()){
            errorMap.put("error", errorClass +
                    "EXCEPTION");
        }else{
            errorMap.put("error", errorClass +
                    "_" +
                    message);
        }
        return errorMap;
    }

    @ExceptionHandler(value = AppUserException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleAppUserException(AppUserException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "USER"));
    }

    @ExceptionHandler(value = EmailException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleEmailException(EmailException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "EMAIL"));
    }

    @ExceptionHandler(value = GenderException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleGenderException(GenderException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "GENDER"));
    }

    @ExceptionHandler(value = LocationException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleLocationException(LocationException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "LOCATION"));
    }

    @ExceptionHandler(value = PasswordException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handlePasswordException(PasswordException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PASSWORD"));
    }

    @ExceptionHandler(value = PrivilegeException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handlePrivilegeException(PrivilegeException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PRIVILEGE"));
    }

    @ExceptionHandler(value = RoleException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleRoleException(RoleException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "ROLE"));
    }
    @ExceptionHandler(value = TokenException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleTokenException(TokenException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "TOKEN"));
    }
    @ExceptionHandler(value = UsernameException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleUsernameException(UsernameException ex) {
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "USERNAME"));
    }

    @ExceptionHandler(value = BadCredentialsException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "PASSWORD"));
    }

    @ExceptionHandler(value = InternalException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleInternalException(BadCredentialsException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "INTERNAL_ERROR"));
    }

    @ExceptionHandler(value = ServletException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleServletException(ServletException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "INTERNAL_ERROR"));
    }

    @ExceptionHandler(value = SpecializationException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleSpecializationException(SpecializationException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "SPECIALIZATION"));
    }

    @ExceptionHandler(value = AvailabilityException.class, produces = "application/json")
    public  ResponseEntity<Map<String, String>> handleAvailabilityException(AvailabilityException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "AVAILABILITY"));
    }

    @ExceptionHandler(value = AppointmentException.class, produces = "application/json")
    public ResponseEntity<Map<String, String>> handleAppointmentException(AppointmentException ex){
        return ResponseEntity.badRequest().body(messageToError(ex.getMessage(), "APPOINTMENT"));
    }
}
