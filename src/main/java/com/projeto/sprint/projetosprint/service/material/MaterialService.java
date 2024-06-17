package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.material.MaterialMapper;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.MaterialMaisColetadoDTO;
import com.projeto.sprint.projetosprint.controller.material.dto.PesagemTotalColetadaDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.domain.repository.MaterialRepository;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.CalcularPorcentagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final CooperativaService cooperativaService;

    public List<Material> findAllMaterials(String email) {
        Integer cooperativeId = cooperativaService.buscarCooperativa(email).getId();
        return materialRepository.findAllByCooperativaId(cooperativeId);
    }

    public MaterialMaisColetadoDTO findMostCollectedMaterial() {
        LocalDate today = LocalDate.now();
        LocalDate monthAgo = today.minusMonths(1);

        String currentMostCollectedMaterial = materialRepository.findMostCollectedMaterialMonth(
                today.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                today.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59));

        String lastMostCollectedMaterial = materialRepository.findMostCollectedMaterialMonth(
                monthAgo.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                monthAgo.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59));

        return new MaterialMaisColetadoDTO(currentMostCollectedMaterial, lastMostCollectedMaterial);
    }

    public Material createMaterial(String email, MaterialCriacaoDTO material) {
        Material createdMaterial = MaterialMapper.of(material);
        createdMaterial.setCooperativa(cooperativaService.buscarCooperativa(email));

        return materialRepository.save(createdMaterial);
    }

    public Material updateMaterial(Integer id, String email, MaterialCriacaoDTO material) {
        Material updatedMaterial = MaterialMapper.of(material);
        updatedMaterial.setId(id);
        updatedMaterial.setCooperativa(cooperativaService.buscarCooperativa(email));
        return materialRepository.save(updatedMaterial);
    }

    public void deleteMaterial(Integer id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
        }
    }

    public PesagemTotalColetadaDTO getTotalWeightCollected(String email) {
        LocalDate mesAtual = LocalDate.now();
        Integer id = cooperativaService.buscarCooperativa(email).getId();

        Double pesoMesAtual = this.materialRepository.getTotalWeightColleted(
                id,
                mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );


        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Double pesoMesAnterior = this.materialRepository.getTotalWeightColleted(
                id,
                mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        double percentual = 0.0;

        if (pesoMesAnterior != 0) {
            percentual = CalcularPorcentagem.porcentagemAumentou(pesoMesAtual, pesoMesAnterior);
        }

        return new PesagemTotalColetadaDTO(pesoMesAtual, pesoMesAnterior, percentual);
    }
}
