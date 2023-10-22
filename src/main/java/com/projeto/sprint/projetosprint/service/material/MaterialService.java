package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.domain.material.Material;
import com.projeto.sprint.projetosprint.domain.repository.MaterialRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }


    public List<Material> listarMateriais(){
        return this.repository.findAll();
    }

    public Material cadastrarMaterial(Material dados){
        return this.repository.save(dados);
    }

    public Material bucarMaterial(int id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Material atualizarMaterial (Material dados){

        if(repository.existsById(dados.getIdMaterial())){
            this.repository.save(dados);
            return dados;
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void deletarMaterial (int id){
        if(repository.existsById(id)){
            this.repository.deleteById(id);
        }
        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }
}
