package com.projeto.sprint.projetosprint.controller.materialColetado;

import com.projeto.sprint.projetosprint.controller.agenda.AgendaMapper;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;

public class MaterialColetadoMapper {
    public static MaterialColetadoResponseDTO of(MaterialColetado materialColetado){
        MaterialColetadoResponseDTO materialDto = new MaterialColetadoResponseDTO();

        materialDto.setId(materialColetado.getId());
        materialDto.setNome(materialColetado.getMaterialPreco().getNome());
        materialDto.setQntKgColeado(materialColetado.getQntKgColetado());
        materialDto.setVlrTotalColedo(materialColetado.getVlrTotalColedo());
        materialDto.setAgendamento(AgendaMapper.of(materialColetado.getAgenda()));
        return materialDto;
    }
}
