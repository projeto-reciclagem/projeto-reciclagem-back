package com.projeto.sprint.projetosprint.service.usuario.cooperativa;

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
