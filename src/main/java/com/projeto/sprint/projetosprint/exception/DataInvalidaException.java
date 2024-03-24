package com.projeto.sprint.projetosprint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataInvalidaException extends RuntimeException{
    public DataInvalidaException(String message) {
        super(message);
    }
}
