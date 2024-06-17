package com.projeto.sprint.projetosprint.controller.material;

import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialMaisColetadoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.PesagemTotalColetadaDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.service.material.MaterialService;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.CurrentUser;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService service;

    @Operation(summary = "Buscar todos os materiais da cooperativa", description = "Essa requisição busca todos os materiais vinculados a cooperativa logada")
    @CurrentUser
    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterialsFromCooperative() {
        String email = UserContextHolder.getUser();
        return ResponseEntity.ok(service.findAllMaterials(email));
    }

    @Operation(summary = "Criar um material vinculado a cooperativa", description = "Essa requisição cria um material vinculado a cooperativa")
    @CurrentUser
    @PostMapping
    public ResponseEntity<Material> addMaterial(@Valid @RequestBody MaterialCriacaoDTO material) {
        String email = UserContextHolder.getUser();
        if (material.getPrecoPorKilo() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Material createdMaterial = this.service.createMaterial(email, material);
        return ResponseEntity.status(201).body(createdMaterial);
    }

    @Operation(summary = "Atualiza os dados do material", description = "Esta requisição atualiza os dados de um material especifico.")
    @CurrentUser
    @PutMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Integer id,
                                                   @RequestBody MaterialCriacaoDTO material) {
        String email = UserContextHolder.getUser();
        return ResponseEntity.ok(this.service.updateMaterial(id, email, material));
    }

    @Operation(summary = "Deleta um material específico")
    @CurrentUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Integer id) {
        this.service.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca o material mais coletado do mês",
            description = "Busca o material mais coletado do mês e também o material mais coletado do mês anterior")
    @CurrentUser
    @GetMapping("/mais-coletado")
    public ResponseEntity<MaterialMaisColetadoDTO> getMostCollectedMaterial() {
        MaterialMaisColetadoDTO mostCollectedMaterial = this.service.findMostCollectedMaterial();
        return ResponseEntity.ok(mostCollectedMaterial);
    }

    @Operation(summary = "Busca a pesagem total coletada do mês",
            description = "Busca a pesagem total coletada do mês, trazendo também o total coletado do mês anterior para comparação")
    @CurrentUser
    @GetMapping("/pesagem-coletada")
    public ResponseEntity<PesagemTotalColetadaDTO> getTotalWeightCollected() {
        String email = UserContextHolder.getUser();
        return ResponseEntity.ok(this.service.getTotalWeightCollected(email));
    }
}
