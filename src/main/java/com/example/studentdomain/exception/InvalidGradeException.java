package com.example.studentdomain.exception;

public class InvalidGradeException  extends  RuntimeException{
    public InvalidGradeException(String message) {
        super(message);
    }

    public InvalidGradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
