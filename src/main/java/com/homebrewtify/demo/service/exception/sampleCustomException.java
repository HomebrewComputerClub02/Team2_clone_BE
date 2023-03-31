package com.homebrewtify.demo.service.exception;
//커스텀 오류 샘플
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
