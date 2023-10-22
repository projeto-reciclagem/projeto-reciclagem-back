package com.projeto.sprint.projetosprint.service.usuario.condominio.dto;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.service.usuario.condominio.CondominioTokenDto;

public class CondominioMapper {

    public  static Condominio of(CondominioCriacaoDto condominioCriacaoDto){
        Condominio condominio = new Condominio();

        condominio.setEmail(condominioCriacaoDto.getEmail());
        condominio.setNome(condominioCriacaoDto.getNome());
        condominio.setCnpj(condominioCriacaoDto.getCnpj());
        condominio.setSenha(condominioCriacaoDto.getSenha());
        condominio.setQtdCasa(condominioCriacaoDto.getQtdCasa());
        condominio.setQtdMoradores(condominioCriacaoDto.getQtdMoradores());

        return condominio;
    }

    public  static CondominioTokenDto of(Condominio condominio, String token){
        CondominioTokenDto condominioTokenDto = new CondominioTokenDto();

        condominioTokenDto.setUserId(condominio.getId());
        condominioTokenDto.setEmail(condominio.getEmail());
        condominioTokenDto.setNome(condominio.getNome());
        condominioTokenDto.setToken(token);

        return condominioTokenDto;
    }
}
