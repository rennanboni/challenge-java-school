package com.challenge.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {
    private static final long serialVersionUID = -3030004524666000548L;

	public DuplicateResourceException(String message) {
        super(message);
    }
}
