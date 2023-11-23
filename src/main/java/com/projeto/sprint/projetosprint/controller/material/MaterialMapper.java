package com.projeto.sprint.projetosprint.controller.material;

import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;

public class MaterialMapper {
    public static MaterialResponseDTO of(Material material) {
        MaterialResponseDTO dto = new MaterialResponseDTO();
        dto.id = material.getId();
        dto.nome = material.getNome();
        dto.valorKg = material.getValorKg();

        if (material.getCooperativa() != null){
            dto.cooperativa = material.getCooperativa();
        }
        return dto;
    }

    public static Material of(MaterialCriacaoDTO dto) {
        Material material = new Material();
        material.setNome(dto.nome);
        material.setValorKg(dto.valorKg);

        if(dto.cooperativa != null){
            material.setCooperativa(dto.cooperativa);
        }

        return material;
    }
}
