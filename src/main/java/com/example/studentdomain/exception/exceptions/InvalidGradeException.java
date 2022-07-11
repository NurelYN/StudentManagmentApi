package com.example.studentdomain.exception.exceptions;

public class InvalidGradeException  extends  RuntimeException{
    public InvalidGradeException(String message) {
        super(message);
    }

    public InvalidGradeException(String message, Throwable cause) {
        super(message, cause);
    }
}
