package com.amiya.spring6restmvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List errorList= exception.getFieldErrors().stream().map(
                fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJPAValidations(TransactionSystemException exception){

        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException constraintViolationException){

            List<Map<String, String>> error = constraintViolationException.getConstraintViolations().stream().map(
                    constraintViolation -> {
                        Map<String ,String> errMap=new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
                        return errMap;
                    }
            ).toList();

            return bodyBuilder.body(error);
        }

        return bodyBuilder.build();
    }
}
