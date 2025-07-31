package com.challenge.school.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.challenge.school.exception.BadRequestException;
import com.challenge.school.exception.DuplicateResourceException;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.exception.SchoolAtMaxCapacityException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);
        Map<String, Object> body = this.getResponse(ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);
        Map<String, Object> body = this.getResponse(ex);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SchoolAtMaxCapacityException.class)
    public ResponseEntity<Object> handleSchoolAtMaxCapacityException(SchoolAtMaxCapacityException ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);
        Map<String, Object> body = this.getResponse(ex);
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleSchoolAtMaxCapacityException(BadRequestException ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);
        Map<String, Object> body = this.getResponse(ex);
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);
    	Map<String, Object> body = new HashMap<>();
        body.put("message", "An unexpected error occurred");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private Map<String, Object> getResponse(Exception ex) {
    	Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return body;
    }
}
