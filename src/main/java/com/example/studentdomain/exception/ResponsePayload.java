package com.example.studentdomain.exception;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ResponsePayload {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;

}
