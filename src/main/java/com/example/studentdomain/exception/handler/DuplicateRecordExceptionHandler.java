package com.example.studentdomain.exception.handler;

import com.example.studentdomain.exception.ResponsePayload;
import com.example.studentdomain.exception.exceptions.DuplicateRecordException;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DuplicateRecordExceptionHandler {

    @ExceptionHandler(value={DuplicateRecordException.class})
    public ResponseEntity<ResponsePayload> handleDuplicateRecord(DuplicateRecordException exception){
        HttpStatus status = HttpStatus.CONFLICT;

        ResponsePayload payload = new ResponsePayload();

        payload.setTimeStamp(LocalDateTime.now());
        payload.setMessage(exception.getMessage());
        payload.setStatus(status);

        return new ResponseEntity<>(payload,status);
    }
}
