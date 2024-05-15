package com.projeto.sprint.projetosprint.controller.condominio.dto;

import lombok.Data;

@Data
public class CondominioSimpleResponseDTO {
    private String nome;
    private String email;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
    private String numero;
}
