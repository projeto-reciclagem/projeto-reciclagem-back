package com.projeto.sprint.projetosprint.service.endereco;

import com.projeto.sprint.projetosprint.controller.endereco.EnderecoMapper;
import com.projeto.sprint.projetosprint.controller.endereco.dto.EnderecoCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;
import com.projeto.sprint.projetosprint.domain.repository.EnderecoRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.util.ApiCepAberto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;
    private final ApiCepAberto apiCep;

    public Endereco cadastrarEndereco(EnderecoCriacaoDTO enderecoDTO){
        Endereco endereco = EnderecoMapper.of(enderecoDTO);

        Optional<ApiCepAberto.Cep> cepOpt = apiCep.searchByCep(endereco.getCep());
        if (cepOpt.isPresent()) throw new EntidadeNaoEncontradaException("O CEP está inválido");

        ApiCepAberto.Cep info = cepOpt.get();
        endereco.setLongitude(info.getLongitude());
        endereco.setLongitude(info.getLatitude());

        return this.repository.save(endereco);
    }

    public Endereco atualizarEndereco(EnderecoCriacaoDTO enderecoDTO, int id){

        Endereco endereco = EnderecoMapper.of(enderecoDTO);
        endereco.setId(id);

        Optional<ApiCepAberto.Cep> cepOpt = apiCep.searchByCep(endereco.getCep());
        if (cepOpt.isPresent()){
            throw new EntidadeNaoEncontradaException("O CEP está inválido");
        }

        if (this.repository.existsById(id)){
            ApiCepAberto.Cep info = cepOpt.get();
            endereco.setLongitude(info.getLongitude());
            endereco.setLongitude(info.getLatitude());

            return this.repository.save(endereco);
        }

        throw new EntidadeNaoEncontradaException("O campo id está inválido");
    }

}
