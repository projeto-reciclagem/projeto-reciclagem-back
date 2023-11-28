package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.material.MaterialMapper;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.domain.repository.MaterialRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository repository;

    private final CooperativaRepository coopRepository;

    public MaterialService(MaterialRepository repository, CooperativaRepository coopRepository) {

        this.repository = repository;
        this.coopRepository = coopRepository;
    }


    public List<Material> listarMateriais(){
        return this.repository.findAll();
    }

    public Material cadastrarMaterial(MaterialCriacaoDTO dados){
        return this.repository.save(MaterialMapper.of(dados));
    }

    public List<Material> uploadTxT(MultipartFile file,Integer id){
        String nome;
        Double kg;
        List<Material> materiais = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            BufferedReader entrada = new BufferedReader(reader);

            String registro = entrada.readLine();

            while(registro != null){
            Material novoMaterial = new Material();
            Cooperativa cooperativa = coopRepository.findById(id).orElseThrow(
                    () -> new EntidadeNaoEncontradaException("Cooperativa não encontrada")
            );
            registro.substring(0,2);
            if (registro.equals("02")){
                novoMaterial.setCooperativa(cooperativa);
                novoMaterial.setNome(registro.substring(2,20));
                novoMaterial.setValorKg(Double.valueOf(registro.substring(20,24)));
                 Material materialCriado = repository.save(novoMaterial);
                 materiais.add(materialCriado);
            }
            registro = entrada.readLine();
            }

            entrada.close();
            return materiais;
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        /*
        BufferedReader entrada = null;
        String registro, tipoRegistro;
        String nomeMaterial;
        Double valor;

        Cooperativa cooperativa = coopRepository.getById(id);

        try{
            entrada = new BufferedReader(new FileReader(file));
        }

         */

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
