package com.projeto.sprint.projetosprint.controller.material;

import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialMaisColetadoDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;

public class MaterialMapper {
    public static Material of(MaterialCriacaoDTO materialCriacaoDTO) {
        Material material = new Material();

        material.setNome(materialCriacaoDTO.getNome());
        material.setPrecoPorKilo(materialCriacaoDTO.getPrecoPorKilo());

        return material;
    }

    public static MaterialMaisColetadoDTO of(String materialAtual, String materialAnterior) {
        MaterialMaisColetadoDTO dto = new MaterialMaisColetadoDTO();

        dto.setMaterialAtual(materialAtual);
        dto.setMaterialAnterior(materialAnterior);

        return dto;
    }
}
