package com.projeto.sprint.projetosprint.service.condominio.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CondominioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
