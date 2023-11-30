package com.projeto.sprint.projetosprint.controller.materialPreco;

import com.projeto.sprint.projetosprint.controller.materialPreco.dto.MaterialPrecoCadastroDTO;
import com.projeto.sprint.projetosprint.controller.materialPreco.dto.MaterialPrecoResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.service.material.MaterialPrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/materiais/precos")
@RequiredArgsConstructor
public class MaterialPrecoController {
    private final MaterialPrecoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<MaterialPrecoResponseDTO> cadastrarMaterialPreco(@RequestBody MaterialPrecoCadastroDTO dados) {
        return ResponseEntity.ok(
            MaterialPrecoMapper.of(
                    this.service.cadastrarMaterialPreco(dados, 0)
        ));
    }
    @GetMapping("/listar")
    public ResponseEntity<List<MaterialPrecoResponseDTO>> listarMaterialPreco(){
        return ResponseEntity.ok(
          this.service.listarMaterialPreco()
                  .stream().map(MaterialPrecoMapper :: of).toList()
        );
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MaterialPrecoResponseDTO> atualizarMaterialPreco(@RequestBody MaterialPrecoCadastroDTO dados, @PathVariable int id) {
        return ResponseEntity.ok(
            MaterialPrecoMapper.of(
                    this.service.cadastrarMaterialPreco(dados, id)
        ));
    }

    @GetMapping("/listar/cooperativa/{id}")
    public ResponseEntity<List<MaterialPrecoResponseDTO>> buscarMaterialPrecoPorIdCooperativa(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.buscarMaterialPrecoPorIdCooperativa(id)
                .stream().map(MaterialPrecoMapper :: of).toList()
        );
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<MaterialPreco>> uploadTxt(@RequestParam MultipartFile file, @PathVariable int id){
        return ResponseEntity.ok(
                this.service.cadastrarMaterialPrecoUpload(file,id)
        );
    }
}
