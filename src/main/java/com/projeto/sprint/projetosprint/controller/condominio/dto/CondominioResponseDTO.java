package com.projeto.sprint.projetosprint.controller.condominio.dto;

import lombok.Data;

@Data
public class CondominioResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cnpj;
    private Integer qtdMoradores;
    private Integer qtdMoradias;
    private Integer qtdBag;
}
