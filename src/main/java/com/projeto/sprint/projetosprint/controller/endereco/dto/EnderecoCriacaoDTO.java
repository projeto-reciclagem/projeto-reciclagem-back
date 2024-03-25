package com.projeto.sprint.projetosprint.controller.endereco.dto;

import lombok.Data;

@Data
public class EnderecoCriacaoDTO {
    private Integer id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String complemento;
}
