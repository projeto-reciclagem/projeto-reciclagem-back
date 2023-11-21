package com.projeto.sprint.projetosprint.dto.cooperativa;


import lombok.Data;

@Data
public class MaterialCooperativaDto {
    private Integer idMaterial;

    private String nomeMaterial;

    private Double valorKg;

    private CooperativaMaterialDto cooperativa;


}
