package com.practice.exceptions;

import com.practice.dtos.ExceptionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalException {
    public ResponseEntity<ExceptionData> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionData(LocalDate.now(),resourceNotFoundException.getMessage(),webRequest.getDescription(true)), HttpStatus.NOT_FOUND);
    }

}
