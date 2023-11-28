package com.projeto.sprint.projetosprint.controller.materialColetado;

import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoCadastroDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoResponseDTO;
import com.projeto.sprint.projetosprint.service.material.MaterialColetadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materiais/coletados")
@RequiredArgsConstructor
public class MaterialColetadoController {
    private final MaterialColetadoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<MaterialColetadoResponseDTO> cadastrarMaterialColetado(@RequestBody MaterialColetadoCadastroDTO dados){
        return ResponseEntity.ok(MaterialColetadoMapper.of(
                this.service.cadastrarMaterialColetado(dados)
        ));
    }
}
