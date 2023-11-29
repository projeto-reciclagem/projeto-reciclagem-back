package com.projeto.sprint.projetosprint.controller.cooperativa.dto;

import lombok.Data;

@Data
public class CooperativaResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cnpj;
    private String ImgUsuario;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
    private String latitude;
    private String longitude;
}
