package com.projeto.sprint.projetosprint.dto.cooperativa.mapper;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.material.Material;
import com.projeto.sprint.projetosprint.dto.cooperativa.CooperativaMaterialDto;
import com.projeto.sprint.projetosprint.dto.cooperativa.MaterialCooperativaDto;

import java.util.List;

public class CooperativaMapper {
    public static CooperativaMaterialDto mapCooperativaMaterialDto(Cooperativa cooperativaEntity) {

        if (cooperativaEntity == null) {
            return null;
        }

        CooperativaMaterialDto cooperativaDto = new CooperativaMaterialDto();

        cooperativaDto.setId(cooperativaEntity.getId());
        cooperativaDto.setNome(cooperativaEntity.getNome());
        cooperativaDto.setCnpj(cooperativaEntity.getCnpj());

        if (!cooperativaEntity.getMaterial().isEmpty()){

            List<Material> materiais = cooperativaEntity.getMaterial();

            List<MaterialCooperativaDto> materialDto = materiais.stream()
                    .map(CooperativaMapper::mapMaterialDto)
                    .toList();

            cooperativaDto.setMaterial(materialDto);
        }

        return cooperativaDto;
    }


    public static MaterialCooperativaDto mapMaterialDto(Material materialEntity) {

        if (materialEntity == null) return null;

        MaterialCooperativaDto materialCooperativaDto = new MaterialCooperativaDto();

        materialCooperativaDto.setIdMaterial(materialEntity.getIdMaterial());
        materialCooperativaDto.setNomeMaterial(materialEntity.getNomeMaterial());
        materialCooperativaDto.setValorKg(materialEntity.getValorKg());


        return materialCooperativaDto;
    }

}
