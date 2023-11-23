package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;

public class CooperativaMapper {

    public static Cooperativa of(CooperativaCriacaoDTO cooperativaCriacaoDTO) {
        Cooperativa cooperativa = new Cooperativa();

        cooperativa.setNome(cooperativaCriacaoDTO.nome);
        cooperativa.setCnpj(cooperativaCriacaoDTO.cnpj);
        return cooperativa;
    }

    public static CooperativaResponseDTO of(Cooperativa cooperativa) {
        CooperativaResponseDTO cooperativaResponseDTO = new CooperativaResponseDTO();

        cooperativaResponseDTO.setId(cooperativa.getId());
        cooperativaResponseDTO.setNome(cooperativa.getNome());
        cooperativaResponseDTO.setCnpj(cooperativa.getCnpj());
        cooperativaResponseDTO.setEmail(cooperativa.getUsuario().getEmail());

        return cooperativaResponseDTO;
    }
}
