package com.projeto.sprint.projetosprint.service.material;

import com.projeto.sprint.projetosprint.controller.materialColetado.MaterialColetadoMapper;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoCadastroDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoResponseDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialPorColetaDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.ValorRecebidoMesDTO;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import com.projeto.sprint.projetosprint.domain.repository.MaterialColetadoRepository;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import com.projeto.sprint.projetosprint.util.PilhaObj;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValorMapper;
import com.projeto.sprint.projetosprint.util.data.AnoMes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        materialColetado.setQntKgColetado(materialDto.getQntKgColeado());
        materialColetado.setVlrTotalColedo(
                materialColetado.getQntKgColetado() * materialPreco.getVlrMaterial()
        );

        materialColetado.setMaterialPreco(materialPreco);
        materialColetado.setAgenda(agendamento);

        return this.repository.save(materialColetado);
    }

    public List<MaterialColetadoResponseDTO> listarMaterialColetadoCooperativa(int idCooperativa){
        return this.repository.findByAgendaCooperativaId(idCooperativa)
                .stream().map(MaterialColetadoMapper :: of).toList();
    }

    public MaterialColetado listarMaterialColetadoCondominio(int idCondominio){
        return this.repository.findByAgendaCondominioId(idCondominio);
    }

    public MaterialColetado listarMaterialColetadoAgenda(int idAgendamento){
        return this.repository.findByAgendaId(idAgendamento);
    }

    public Double totalColetadoUltimaSemana(int idCooperativa){
        LocalDateTime dataAtual = LocalDateTime.now();

        Double totalColetado = this.repository.totalColetadoUltimaSemana(
                idCooperativa,
                dataAtual.minusWeeks(1),
                dataAtual);
        return totalColetado;
    }

    public MaterialUltimaSemana buscarMaterialMaisReciclado(int idCooperativa){
        LocalDateTime dataAtual = LocalDateTime.now();

        String materialMaisReciclado = this.repository.buscarMaterialMaisReciclado(
                idCooperativa,
                dataAtual.minusWeeks(1),
                dataAtual
        );

        MaterialUltimaSemana material = new MaterialUltimaSemana();
        int i = materialMaisReciclado.indexOf(",");
        material.setNome(materialMaisReciclado.substring(0, i));
        material.setQntKgColetada(Double.valueOf(materialMaisReciclado.substring(i + 1 , materialMaisReciclado.length())));

        return material;
    }

    public Map<String, Double> reciclagemSemanal(int idCooperativa) {
        LocalDateTime dataAtual = LocalDateTime.now();

        List<MaterialColetado> materiais = this.repository.reciclagemSemanal(
                idCooperativa,
                dataAtual.minusWeeks(1),
                dataAtual
        );

        Map<DayOfWeek, Double> sumByDayOfWeek = materiais.stream()
                .collect(Collectors.groupingBy(
                        date -> date.getAgenda().getDatAgendamento().getDayOfWeek(),
                        Collectors.summingDouble(date -> date.getQntKgColetado())
                ));

        Map<String, Double> result = new TreeMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            result.put(day.toString(), sumByDayOfWeek.getOrDefault(day, 0.0));
        }

        return result;
    }

    public Map<String, Double> porcentagemPorMaterial(int idCooperativa){
        List<MaterialColetadoResponseDTO> listMateriais = this.listarMaterialColetadoCooperativa(idCooperativa);

        Map<String, Double> mapQuantidadeMaterial = listMateriais.stream()
                .collect(Collectors.groupingBy(material -> material.getNome(),
                        Collectors.summingDouble(material -> material.getQntKgColetado())
                ));

        Double total = this.repository.quantidadeKgTotal(idCooperativa);

        for (Map.Entry<String, Double> map : mapQuantidadeMaterial.entrySet()) {
            Double valor = map.getValue();
            Double valorPorcentagem =  (valor * 100) / total;
            mapQuantidadeMaterial.put(map.getKey(), (double) Math.round(valorPorcentagem));
        }
        return mapQuantidadeMaterial;
    }

    public Double valorTotalUltimoMes(int idCondominio){
        LocalDateTime dataAtual = LocalDateTime.now();

        return this.repository.totalColetadoUltimaSemana(
                idCondominio,
                dataAtual.minusMonths(1),
                dataAtual);
    }

    public List<MaterialPorColetaDTO> kgMaterialPorColeta(int id){
        LocalDateTime dataAtual = LocalDateTime.now();

        List<MaterialColetado> listMateriais = this.repository.materialPorColetaAno(
                id,
                dataAtual.minusYears(1),
                dataAtual
        );

        Map<String, List<ChaveValor>> map = new TreeMap<>();

        for (MaterialColetado material : listMateriais){
            Month mes = material.getAgenda().getDatRetirada().getMonth();
            int ano = material.getAgenda().getDatRetirada().getYear();

            String anoMes = mes+"/"+ano;

            Boolean exists = false;
            for (Map.Entry<String, List<ChaveValor>> mapAnoMes : map.entrySet()){
                String a = mapAnoMes.getKey();
                if(a.equals(anoMes)){
                    exists = true;
                }
            }

            if(!exists){
                map.put(anoMes, new ArrayList<>());
            }

            boolean encontrouChave = false;
            for (ChaveValor chaveValor : map.get(anoMes)) {
                if (chaveValor.getChave().equals(material.getMaterialPreco().getNome())) {
                    // Se encontrou a chave, soma o valor existente
                    chaveValor.setValor(chaveValor.getValor() + material.getQntKgColetado());
                    encontrouChave = true;
                    break;
                }
            }
            if (!encontrouChave) {
                map.get(anoMes).add(new ChaveValor(material.getMaterialPreco().getNome(), material.getQntKgColetado()));
            }
        }
        return ChaveValorMapper.mapperMaterialPorColeta(map);
    }

    public List<ValorRecebidoMesDTO> valorRecebidoPorMes(int idCondominio){
        List<MaterialPorColetaDTO> listMaterial = kgMaterialPorColeta(idCondominio);
        List<ValorRecebidoMesDTO> listValorRecebido = new ArrayList<>();

        for (MaterialPorColetaDTO materialDTO : listMaterial) {
            ValorRecebidoMesDTO valorRecebido = new ValorRecebidoMesDTO();
            valorRecebido.setMes(materialDTO.getData());
            valorRecebido.setValor(0.0);

            for (ChaveValor c : materialDTO.getValor()) {
                valorRecebido.setValor(valorRecebido.getValor() + c.getValor());
            }
            listValorRecebido.add(valorRecebido);
        }
        return listValorRecebido;
    }
}
