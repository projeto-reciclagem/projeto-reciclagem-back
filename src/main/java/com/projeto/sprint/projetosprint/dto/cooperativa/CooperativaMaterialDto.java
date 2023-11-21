package com.projeto.sprint.projetosprint.dto.cooperativa;

import lombok.Data;

import java.util.List;

@Data
public class CooperativaMaterialDto {

    private Integer id;

    private String nome;

    private String cnpj;

    private List<MaterialCooperativaDto> material;

}
