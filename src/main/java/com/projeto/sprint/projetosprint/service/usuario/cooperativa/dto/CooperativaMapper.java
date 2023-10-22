package com.projeto.sprint.projetosprint.service.usuario.cooperativa.dto;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.CooperativaTokenDto;

public class CooperativaMapper {

    public  static Cooperativa of(CooperativaCriacaoDto cooperativaCriacaoDto){
        Cooperativa cooperativa = new Cooperativa();

        cooperativa.setEmail(cooperativaCriacaoDto.getEmail());
        cooperativa.setNome(cooperativaCriacaoDto.getNome());
        cooperativa.setCnpj(cooperativaCriacaoDto.getCnpj());
        cooperativa.setSenha(cooperativaCriacaoDto.getSenha());

        return cooperativa;
    }



    public static CooperativaTokenDto of(Cooperativa cooperativa, String token){
        CooperativaTokenDto cooperativaTokenDto = new CooperativaTokenDto();

        cooperativaTokenDto.setUserId(cooperativa.getId());
        cooperativaTokenDto.setEmail(cooperativa.getEmail());
        cooperativaTokenDto.setNome(cooperativa.getNome());
        cooperativaTokenDto.setToken(token);

        return  cooperativaTokenDto;
    }
}
