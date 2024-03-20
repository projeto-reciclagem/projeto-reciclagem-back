package com.projeto.sprint.projetosprint.service.material;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.projeto.sprint.projetosprint.controller.materialColetado.MaterialColetadoMapper;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.*;
import com.projeto.sprint.projetosprint.domain.entity.agenda.Agenda;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialColetado;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import com.projeto.sprint.projetosprint.domain.repository.MaterialColetadoRepository;
import com.projeto.sprint.projetosprint.exception.ImportacaoExportacaoException;
import com.projeto.sprint.projetosprint.service.agenda.AgendaService;
import com.projeto.sprint.projetosprint.util.*;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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
        return totalColetado != null ? totalColetado : 0;
    }

    public MaterialUltimaSemana buscarMaterialMaisReciclado(int idCooperativa){
        LocalDateTime dataAtual = LocalDateTime.now();

        String materialMaisReciclado = this.repository.buscarMaterialMaisReciclado(
                idCooperativa,
                dataAtual.minusWeeks(1),
                dataAtual
        );

        MaterialUltimaSemana material = new MaterialUltimaSemana();

        if (materialMaisReciclado != null){

            int i = materialMaisReciclado.indexOf(",");

            material.setNome(materialMaisReciclado.substring(0, i));
            material.setQntKgColetada(Double.valueOf(materialMaisReciclado.substring(i + 1 , materialMaisReciclado.length())));
        }

        return material;
    }

    public List<ChaveValor> reciclagemSemanal(int idCooperativa) {
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
        return DiasSemana.converterDias(result);
    }

    public List<ChaveValor> porcentagemPorMaterial(int idCooperativa){
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
        return FilaMaterialReciclado.converterList(mapQuantidadeMaterial);
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


    public byte[] downloadMaterialColetaCsv(int id){
        List<MaterialPorColetaDTO> listMaterial = kgMaterialPorColeta(id);

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Mês", "Valores"};
            csvWriter.writeNext(cabecalho);

            for (int i = 0; i < listMaterial.size(); i++){
                MaterialPorColetaDTO m = listMaterial.get(i);

                String valorMateriais = "";
                for(ChaveValor c : m.getValor()){
                    valorMateriais += c.getChave() + ":"+ c.getValor() + ",";
                }
                //GRAVANDO OS DADOS DA COOPERATIVA NO ARQUIVO
                String[] linha = {
                        m.getData(),
                        valorMateriais
                };
                csvWriter.writeNext(linha);
            }
            csvWriter.close();
            outputStreamWriter.close();
            byte[] csvBytes = byteArrayOutputStream.toByteArray();
            return csvBytes;
        }
        catch (IOException e) {
            throw new ImportacaoExportacaoException(e.getMessage());
        }
    }

    public byte[] downloadValorRecebidoCsv(int id){
        List<ValorRecebidoMesDTO> listValor = valorRecebidoPorMes(id);

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);

            ICSVWriter csvWriter = new CSVWriterBuilder(outputStreamWriter)
                    .withSeparator(';')
                    .build();

            String[] cabecalho = {"Mês", "Valor"};
            csvWriter.writeNext(cabecalho);

            for (int i = 0; i < listValor.size(); i++){
                ValorRecebidoMesDTO v = listValor.get(i);

                //GRAVANDO OS DADOS DA COOPERATIVA NO ARQUIVO
                String[] linha = {
                        v.getMes(),
                        v.getValor().toString()
                };
                csvWriter.writeNext(linha);
            }
            csvWriter.close();
            outputStreamWriter.close();
            byte[] csvBytes = byteArrayOutputStream.toByteArray();
            return csvBytes;
        }
        catch (IOException e) {
            throw new ImportacaoExportacaoException(e.getMessage());
        }
    }

    public ColetasUltimoMesDTO coletasUltimoMes(int id){

        LocalDate mesAtual = LocalDate.now();
        Integer totalMesAtual = this.repository.materialColetadoMes(
                id,
                mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        LocalDate mesAnterior = mesAtual.minusMonths(1);
        Integer totalMesAnterior = this.repository.materialColetadoMes(
                id,
                mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );

        Double p = 0.0;

        if (totalMesAnterior != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(totalMesAtual, totalMesAnterior);
        }

        ColetasUltimoMesDTO coletas = new ColetasUltimoMesDTO(totalMesAtual,totalMesAnterior,p.intValue());
        return coletas;
    }

    public ColetasUltimoMesDTO quantidadeBagsUltimoMes(int id){

        LocalDate mesAtual = LocalDate.now();

        Integer bgsMesAtual = this.repository.bagsColetadasMes(
                id,
                mesAtual.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAtual.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );
        bgsMesAtual = bgsMesAtual == null ? 0 : bgsMesAtual;


        LocalDate mesAnterior = mesAtual.minusMonths(1);

        Integer bgsMesAnterior = this.repository.bagsColetadasMes(
                id,
                mesAnterior.with(TemporalAdjusters.firstDayOfMonth()).atTime(0,0,0),
                mesAnterior.with(TemporalAdjusters.lastDayOfMonth()).atTime(23,59,59)
        );
        bgsMesAnterior = bgsMesAnterior == null ? 0 : bgsMesAnterior;

        Double p = 0.0;

        if (bgsMesAnterior != 0) {
            p = CalcularPorcentagem.porcentagemAumentou(bgsMesAtual, bgsMesAnterior);
        }

        ColetasUltimoMesDTO coletas = new ColetasUltimoMesDTO(bgsMesAtual,bgsMesAnterior,p.intValue());

        return coletas;
    }
}
