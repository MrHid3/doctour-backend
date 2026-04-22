package com.doctour.doctourbe.exception;

public class PrivilegeNotFoundException extends RuntimeException {

    public PrivilegeNotFoundException() {}

    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}
