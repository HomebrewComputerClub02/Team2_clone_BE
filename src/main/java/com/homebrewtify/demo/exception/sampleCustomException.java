package com.homebrewtify.demo.exception;

public class sampleCustomException extends RuntimeException{
    public sampleCustomException() {
        super();
    }

    public sampleCustomException(String message) {
        super(message);
    }

    public sampleCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public sampleCustomException(Throwable cause) {
        super(cause);
    }
}
