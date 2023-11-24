package com.projeto.sprint.projetosprint.controller.endereco;

import com.projeto.sprint.projetosprint.controller.endereco.dto.EnderecoCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;

public class EnderecoMapper {
    public static Endereco of(EnderecoCriacaoDTO enderecoCriacaoDTO){
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoCriacaoDTO.getCep());
        endereco.setLogradouro(enderecoCriacaoDTO.getLogradouro());
        endereco.setBairro(enderecoCriacaoDTO.getBairro());
        endereco.setCidade(enderecoCriacaoDTO.getCidade());
        endereco.setComplemento(enderecoCriacaoDTO.getComplemento());

        return endereco;
    }
}
