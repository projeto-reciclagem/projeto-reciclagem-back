package com.projeto.sprint.projetosprint.controller.material;



import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.service.material.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/materiais")
//@AllArgsConstructor
public class MaterialController {

    private final MaterialService service;
    public MaterialController(MaterialService service) {
        this.service = service;
    }

    //Lista de Materiais
    @GetMapping("/listar")
    public ResponseEntity<List<MaterialResponseDTO>> listarMateriais(){

        List<Material> materiais = this.service.listarMateriais();
        if(materiais.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(materiais.stream()
                .map(MaterialMapper::of).toList());
    }

    //Buscando material por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<MaterialResponseDTO> buscarMaterial(@PathVariable int id){

        Material registro = this.service.bucarMaterial(id);

        return ResponseEntity.ok(MaterialMapper.of(registro));
    }

    //Cadastrando Materiais
    @PostMapping("/cadastrar")
    public ResponseEntity<MaterialResponseDTO> cadastrarMaterial(@Valid @RequestBody MaterialCriacaoDTO dados) {
        Material materialSalvo = this.service.cadastrarMaterial(dados);
        return ResponseEntity.status(201).body(MaterialMapper.of(materialSalvo));
    }

    // Atualizar os dados dos materiais
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MaterialResponseDTO> atualizarMaterial(@PathVariable int id,
                                                      @RequestBody @Valid MaterialCriacaoDTO dados) {

        return ResponseEntity.ok(
                MaterialMapper.of(
                this.service.atualizarMaterial(dados, id)));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarMaterialPorId(@PathVariable int id){

        this.service.deletarMaterial(id);
        return ResponseEntity.status(204).build();
    }



}
