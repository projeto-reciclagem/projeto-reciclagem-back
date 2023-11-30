package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialPreco;
import com.projeto.sprint.projetosprint.exception.ImportacaoExportacaoException;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.service.material.MaterialPrecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;

import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/cooperativas")
@RequiredArgsConstructor
public class CooperativaController {
    private final CooperativaService service;
    private final MaterialPrecoService materialPrecoService;

    //LISTA TODAS AS COOPERATIVAS
    @GetMapping("/listar")
    public ResponseEntity<List<CooperativaResponseDTO>> listarCooperativa(){

        List<CooperativaResponseDTO> cooperativas = this.service.listarCooperativa();

        return cooperativas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cooperativas);
    }

    //BUSCA A COOPERATIVA POR ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CooperativaResponseDTO> buscarCooperativaPorId(@PathVariable int id) {
        Cooperativa cooperativa = this.service.buscaCoperativaId(id);
        return ResponseEntity.ok(CooperativaMapper.of(cooperativa));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CooperativaResponseDTO> cadastrarCooperativa(@Valid  @RequestBody CooperativaCriacaoDTO dados){

        Cooperativa cooperativaSalva = this.service.cadastrarCooperativa(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cooperativaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(CooperativaMapper.of(cooperativaSalva));
    }

    //ATUALIZANDO INFORMAÇÕES DA COOPERATIVA
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarCooperativa(@PathVariable int id,
                                                     @Valid @RequestBody CooperativaAtualizarDTO dados){

        this.service.atualizarCooperativa(dados, id);
        return ResponseEntity.noContent().build();
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCooperativaPorId(@PathVariable int id){

        this.service.deletarCooperativa(id);
        return ResponseEntity.noContent().build();
    }
    
    //GRAVANDO OS DADOS EM UM ARQUIVO CSV
    @PostMapping( "{id}/export/csv")
    public ResponseEntity gravaArquivoCsv(@PathVariable int id) {
        try{
            byte[] csvData = this.service.downloadCooperativaCsv(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=cooperativas_" +
                            LocalDateTime.now()
                                    .toString()
                                    .replace(":", "-")
                                    .replace(".", "-") +
                            ".csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        }
        catch (Exception e){
            throw new ImportacaoExportacaoException(e.getMessage());
        }
    }

    @PostMapping("/export/txt/{id}")
    public ResponseEntity<byte[]>downloadTxT(@PathVariable Integer id){
        Cooperativa cooperativa = this.service.buscaCoperativaId(id);

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
            String header = "00COOPERATIVA";
            header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            outputStreamWriter.write(header+"\n");

            String corpo = "02";
            corpo += String.format("%-25.25s", cooperativa.getNome());
            corpo += String.format("%-16.16s", cooperativa.getCnpj());
            corpo += String.format("%-30.30s", cooperativa.getUsuario().getEmail());

            outputStreamWriter.write(corpo+"\n");
            List<MaterialPreco> listMaterial = this.materialPrecoService.buscarMaterialCooperativaValor(id);

            String[] materiais = new String[]{"PET", "Ferro", "Papelão","Alumínio"};

            for (String material : materiais){
                String corpo2 = "03";
                corpo2 += String.format("%-17.17s","Material-"+material);
                Double valor = 0.0;
                for(MaterialPreco m : listMaterial){
                    if (m.getNome().equals(material)){
                        valor = m.getVlrMaterial();
                    }
                        corpo2 += String.format("%3.2f",valor);
                }
                outputStreamWriter.write(corpo2+"\n");
            }

            String trailer = "01";
            trailer += String.format("%10d",listMaterial.size() +1);

            outputStreamWriter.write(trailer);
            outputStreamWriter.close();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-disposition","attachament; filename=registro-coleta.txt");
            httpHeaders.add("content-type","text/csv; charset=UTF-8");
            return ResponseEntity.status(200).headers(httpHeaders).body(byteArrayOutputStream.toByteArray());
        }
        catch (IOException erro){
            throw  new RuntimeException(erro);
        }
    }
}
