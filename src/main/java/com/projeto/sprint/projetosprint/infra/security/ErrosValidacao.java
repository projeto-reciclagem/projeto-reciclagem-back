package com.projeto.sprint.projetosprint.infra.security;

import org.springframework.validation.FieldError;

public record ErrosValidacao(String campo, String erro) {
    public  ErrosValidacao(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
