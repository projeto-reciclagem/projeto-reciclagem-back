package com.projeto.sprint.projetosprint.service.cooperativa;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.util.ListaObj;
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

    public Cooperativa buscaCoperativaId(int id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Cooperativa cadastrarCooperativa(Cooperativa dados){

        if(this.repository.existsByEmail(dados.getEmail())){
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

    public List<Cooperativa> listarCooperativasGenerico(){
        return this.repository.findAll();
    }


}
