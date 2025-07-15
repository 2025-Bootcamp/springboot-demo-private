package com.bootcamp.springBootDemo.exception;

public class InvalidEmployeeException extends  RuntimeException {
    public InvalidEmployeeException(String message) {
        super(message);
    }
}
