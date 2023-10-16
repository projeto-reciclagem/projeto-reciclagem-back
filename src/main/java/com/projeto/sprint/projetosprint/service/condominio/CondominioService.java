package com.projeto.sprint.projetosprint.service.condominio;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository repository;

    public List<Condominio> listarCondominio(){
        return this.repository.findAll();
    }

    public Condominio buscaCondominioId(Integer id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Condominio cadastrarCondominio(Condominio dados) {

        if(this.repository.existsByEmail(dados.getEmail())){
            throw new EntidadeDuplicadaException("Email já cadastrado");
        }

        return this.repository.save(dados);
    }

    public Condominio atualizarCondominio(Condominio dados){
        if(this.repository.existsById(dados.getId())){
            return this.repository.save(dados);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void deletarCondominio(Integer id){
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }
}
