package com.projeto.sprint.projetosprint.controller.endereco.dto;

import lombok.Data;

@Data
public class EnderecoCriacaoDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
}
