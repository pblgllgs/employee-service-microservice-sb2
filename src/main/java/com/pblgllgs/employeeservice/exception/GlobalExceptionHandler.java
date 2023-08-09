package com.pblgllgs.employeeservice.exception;

import com.pblgllgs.employeeservice.entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest
    ) {
        return new ResponseEntity<>(
                new ErrorDetails(
                        new Date(),
                        resourceNotFoundException.getMessage(),
                        webRequest.getDescription(false)
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
