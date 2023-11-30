package com.projeto.sprint.projetosprint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImportacaoExportacaoException extends RuntimeException{
    public ImportacaoExportacaoException(String message){
        super(message);
    }
}
