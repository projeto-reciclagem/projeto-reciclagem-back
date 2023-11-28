package com.projeto.sprint.projetosprint.controller.materialPreco;

import com.projeto.sprint.projetosprint.controller.materialPreco.dto.MaterialPrecoResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;

public class MaterialPrecoMapper {
    public static MaterialPrecoResponseDTO of(MaterialPreco material){
        MaterialPrecoResponseDTO materialPreco = new MaterialPrecoResponseDTO();

        materialPreco.setId(material.getId());
        materialPreco.setNome(material.getNome());
        materialPreco.setVlrMaterial(material.getVlrMaterial());
        return materialPreco;
    }
}
