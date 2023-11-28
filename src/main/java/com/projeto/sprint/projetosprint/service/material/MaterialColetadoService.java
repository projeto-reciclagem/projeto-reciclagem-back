package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoCadastroDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.domain.repository.MaterialColetadoRepository;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialColetadoService {
    private final MaterialColetadoRepository repository;
    private final AgendaService agendaService;
    private final MaterialPrecoService materialPrecoService;

    public MaterialColetado cadastrarMaterialColetado(MaterialColetadoCadastroDTO materialDto){
        MaterialPreco materialPreco = this.materialPrecoService.buscarMaterialPrecoPorId(materialDto.getFkMaterialPreco());
        Agenda agendamento = this.agendaService.buscarAgendaPorId(materialDto.getFkAgendamento());

        MaterialColetado materialColetado = new MaterialColetado();
        materialColetado.setQntKgColeado(materialDto.getQntKgColeado());
        materialColetado.setVlrTotalColedo(
                materialColetado.getQntKgColeado() * materialPreco.getVlrMaterial()
        );

        materialColetado.setMaterialPreco(materialPreco);
        materialColetado.setAgenda(agendamento);

        return this.repository.save(materialColetado);
    }

    public MaterialColetado listarMaterialColetadoCooperativa(int idCooperativa){
        return this.repository.findByAgendaCooperativaId(idCooperativa);
    }

    public MaterialColetado listarMaterialColetadoCondominio(int idCondominio){
        return this.repository.findByAgendaCondominioId(idCondominio);
    }

    public MaterialColetado listarMaterialColetadoAgenda(int idAgendamento){
        return this.repository.findByAgendaId(idAgendamento);
    }
}