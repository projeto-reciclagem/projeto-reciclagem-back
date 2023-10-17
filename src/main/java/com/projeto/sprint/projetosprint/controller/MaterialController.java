package com.projeto.sprint.projetosprint.controller;


import com.projeto.sprint.projetosprint.entity.Material;

import com.projeto.sprint.projetosprint.repository.MaterialRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materiais")
//@AllArgsConstructor
public class MaterialController {

    private final MaterialRepository repository;
    public MaterialController(MaterialRepository repository){
        this.repository = repository;
    }

    //Lista de Materiais
    @GetMapping
    public ResponseEntity<List<Material>> listarMateriais(){
        List<Material> materiais = this.repository.findAll();
        if(materiais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materiais);
    }
    //Buscando material por ID
    @GetMapping("/buscar-material-por-id/{id}")
    public ResponseEntity<Material> buscarMaterial(@PathVariable int id){

        Optional<Material> registroOpt = this.repository.findById(id);

        if(registroOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200)
                .body(registroOpt.get());
    }

    //Cadastrando Materiais
    @PostMapping("/cadastrar-material")
    public ResponseEntity<Material> cadastrarMaterial(@RequestBody @Valid Material dados) {
        Material materialSalvo = this.repository.save(dados);
        return ResponseEntity.status(201).body(materialSalvo);
    }

    // Atualizar os dados dos materiais
    @PutMapping("/atualizar-material-por-id/{id}")
    public ResponseEntity<Material> atualizarMaterial(@PathVariable int id, @RequestBody @Valid Material dados) {
        Optional<Material> materialOpt = this.repository.findById(id);
        if (materialOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Material não encontrado");
        }

        Material materialSalvo = materialOpt.get();
        materialSalvo.setNomeMaterial(dados.getNomeMaterial());
        materialSalvo.setValorKg(dados.getValorKg());

        this.repository.save(materialSalvo);
        return ResponseEntity.status(200).body(materialSalvo);
    }


    @DeleteMapping("/deletar-material-por-id/{id}")
    public ResponseEntity<Void> deletarMaterialPorId(@PathVariable int id){

        if(!this.repository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        this.repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }



}
