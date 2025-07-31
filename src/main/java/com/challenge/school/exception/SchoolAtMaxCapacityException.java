package com.challenge.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class SchoolAtMaxCapacityException extends RuntimeException {
    private static final long serialVersionUID = -1081485624146719688L;

	public SchoolAtMaxCapacityException(String message) {
        super(message);
    }
}
