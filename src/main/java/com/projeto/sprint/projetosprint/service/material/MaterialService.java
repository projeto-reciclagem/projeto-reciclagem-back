package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.material.MaterialMapper;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.domain.repository.MaterialRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }


    public List<Material> listarMateriais(){
        return this.repository.findAll();
    }

    public Material cadastrarMaterial(MaterialCriacaoDTO dados){
        return this.repository.save(MaterialMapper.of(dados));
    }

    public Material bucarMaterial(int id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Material atualizarMaterial (MaterialCriacaoDTO dados, int id){

        Material material = MaterialMapper.of(dados);
        if (repository.existsById(id)) {
            material.setId(id);
            return this.repository.save(material);
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
