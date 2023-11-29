package com.projeto.sprint.projetosprint.controller.condominio.dto;

import lombok.Data;

@Data
public class CondominioResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String ImgUsuario;

    private String cnpj;
    private Integer qtdMoradores;
    private Integer qtdMoradias;
    private Integer qtdBag;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
    private String latitude;
    private String longitude;
}
