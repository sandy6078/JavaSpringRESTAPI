package com.javaspringrestapi.demo;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class InternalServerErrorAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, 
    	HttpServletRequest request, HttpServletResponse response) {
    	//Handle DataIntegrityViolationException when input incorrect
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (ex instanceof DataIntegrityViolationException) { 
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}