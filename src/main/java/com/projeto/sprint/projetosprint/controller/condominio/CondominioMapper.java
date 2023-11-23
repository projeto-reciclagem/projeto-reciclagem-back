package com.projeto.sprint.projetosprint.controller.condominio;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;

public class CondominioMapper {
    public static Condominio of(CondominioCriacaoDTO coondominioCriacaoDTO){
        Condominio condominio = new Condominio();

        condominio.setNome(coondominioCriacaoDTO.nome);
        condominio.setCnpj(coondominioCriacaoDTO.cnpj);
        condominio.setQtdMoradores(coondominioCriacaoDTO.qtdMoradores);
        condominio.setQtdMoradias(coondominioCriacaoDTO.qtdMoradias);
        condominio.setQtdBag(coondominioCriacaoDTO.qtdBag);
        return condominio;
    }

    public static CondominioResponseDTO of(Condominio condominio){
        CondominioResponseDTO condominioResponseDTO = new CondominioResponseDTO();

        condominioResponseDTO.setId(condominio.getId());
        condominioResponseDTO.setNome(condominio.getNome());
        condominioResponseDTO.setCnpj(condominio.getCnpj());
        condominioResponseDTO.setEmail(condominio.getUsuario().getEmail());

        condominioResponseDTO.setQtdMoradores(condominio.getQtdMoradores());
        condominioResponseDTO.setQtdMoradias(condominio.getQtdMoradias());
        condominioResponseDTO.setQtdBag(condominio.getQtdBag());

        return condominioResponseDTO;
    }
}
