package com.projeto.sprint.projetosprint.service.cooperativa.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooperativaTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
