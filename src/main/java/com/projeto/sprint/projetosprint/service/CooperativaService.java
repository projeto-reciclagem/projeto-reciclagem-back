package com.projeto.sprint.projetosprint.service;

import com.projeto.sprint.projetosprint.entity.Cooperativa;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.repository.CooperativaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CooperativaService {
    private final CooperativaRepository repository;

    public CooperativaService(CooperativaRepository repository) {
        this.repository = repository;
    }

    public List<Cooperativa> listarCooperativa(){
        return this.repository.findAll();
    }

    public Cooperativa buscaCoperativaId(Integer id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Cooperativa cadastrarCooperativa(Cooperativa dados){

        if(this.repository.countByEmailContainsIgnoreCase(dados.getEmail()) > 0){
            throw new EntidadeDuplicadaException("Email já cadastrado");
        }

        return this.repository.save(dados);
    }

    public Cooperativa atualizarCooperativa(Cooperativa dados){
        if(this.repository.existsById(dados.getId())){
            return this.repository.save(dados);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void deletarCooperativa(Integer id){
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }
}
