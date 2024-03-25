package com.projeto.sprint.projetosprint.controller.endereco;

import com.projeto.sprint.projetosprint.controller.endereco.dto.EnderecoCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;

public class EnderecoMapper {
    public static Endereco of(EnderecoCriacaoDTO enderecoCriacaoDTO){
        Endereco endereco = new Endereco();

        endereco.setId(enderecoCriacaoDTO.getId());
        endereco.setCep(enderecoCriacaoDTO.getCep());
        endereco.setNumero(enderecoCriacaoDTO.getNumero());
        endereco.setLogradouro(enderecoCriacaoDTO.getLogradouro());
        endereco.setBairro(enderecoCriacaoDTO.getBairro());
        endereco.setCidade(enderecoCriacaoDTO.getCidade());
        endereco.setComplemento(enderecoCriacaoDTO.getComplemento());

        return endereco;
    }

    public static EnderecoCriacaoDTO of(Endereco endereco){
        EnderecoCriacaoDTO enderecoDTO = new EnderecoCriacaoDTO();


        enderecoDTO.setId(endereco.getId());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setBairro(endereco.getBairro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setComplemento(endereco.getComplemento());

        return enderecoDTO;
    }
}
