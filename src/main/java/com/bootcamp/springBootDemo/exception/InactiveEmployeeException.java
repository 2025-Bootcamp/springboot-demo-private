package com.bootcamp.springBootDemo.exception;

public class InactiveEmployeeException extends  RuntimeException {

    public InactiveEmployeeException(String message) {
        super(message);
    }
}
