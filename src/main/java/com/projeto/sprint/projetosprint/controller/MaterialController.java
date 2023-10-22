package com.projeto.sprint.projetosprint.controller;



import com.projeto.sprint.projetosprint.domain.repository.MaterialRepository;
import com.projeto.sprint.projetosprint.domain.material.Material;
import com.projeto.sprint.projetosprint.service.material.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materiais")
//@AllArgsConstructor
public class MaterialController {

    private final MaterialService service;
    public MaterialController(MaterialService service) {
        this.service = service;
    }

    //Lista de Materiais
    @GetMapping("/listar-materiais")
    public ResponseEntity<List<Material>> listarMateriais(){

        List<Material> materiais = this.service.listarMateriais();
        if(materiais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materiais);
    }

    //Buscando material por ID
    @GetMapping("/buscar-material-por-id/{id}")
    public ResponseEntity<Material> buscarMaterial(@PathVariable int id){

        Material registro = this.service.bucarMaterial(id);

        return ResponseEntity.ok(registro);
    }

    //Cadastrando Materiais
    @PostMapping("/cadastrar-material")
    public ResponseEntity<Material> cadastrarMaterial(@RequestBody @Valid Material dados) {
        Material materialSalvo = this.service.cadastrarMaterial(dados);
        return ResponseEntity.status(201).body(materialSalvo);
    }

    // Atualizar os dados dos materiais
    @PutMapping("/atualizar-material/{id}")
    public ResponseEntity<Material> atualizarMaterial(@PathVariable int id, @RequestBody @Valid Material dados) {

        dados.setIdMaterial(id);

        return ResponseEntity.ok(
                this.service.atualizarMaterial(dados)
        );
    }

    @DeleteMapping("/deletar-material/{id}")
    public ResponseEntity<Void> deletarMaterialPorId(@PathVariable int id){

        this.service.deletarMaterial(id);
        return ResponseEntity.status(204).build();
    }



}
