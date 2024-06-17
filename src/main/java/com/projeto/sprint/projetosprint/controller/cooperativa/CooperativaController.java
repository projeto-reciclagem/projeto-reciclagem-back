package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.exception.ImportacaoExportacaoException;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.CurrentUser;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/cooperativas")
@RequiredArgsConstructor
public class CooperativaController {
    private final CooperativaService service;

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
        Cooperativa cooperativa = this.service.buscarCoperativaId(id);
        return ResponseEntity.ok(CooperativaMapper.of(cooperativa));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CooperativaResponseDTO> cadastrarCooperativa(@Valid  @RequestBody CooperativaCriacaoDTO dados){

        Cooperativa cooperativaSalva = this.service.cadastrarCooperativa(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cooperativaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(CooperativaMapper.of(cooperativaSalva));
    }

    //ATUALIZANDO INFORMAÇÕES DA COOPERATIVA
    @Operation(summary = "Atualizar informações da cooperativa", description = "Atualiza as informações da cooperativa, desde os dados pessoais até endereço")
    @CurrentUser
    @PatchMapping("/atualizar")
    public ResponseEntity<Void> atualizarCooperativa(@Valid @RequestBody CooperativaAtualizarDTO dados) {
        String email = UserContextHolder.getUser();
        Cooperativa cooperativa = this.service.buscarCooperativa(email);
        this.service.atualizarCooperativa(dados, cooperativa);
        return ResponseEntity.noContent().build();
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarCooperativaPorId(@RequestHeader(HttpHeaders.COOKIE) String auth){
        Cooperativa cooperativa = this.service.buscarCooperativa(auth.replace("auth=", ""));

        this.service.deletarCooperativa(cooperativa.getId());
        return ResponseEntity.noContent().build();
    }
    
    //GRAVANDO OS DADOS EM UM ARQUIVO CSV
    @PostMapping( "/{id}/export/csv")
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

//    @PostMapping("/{id}/export/txt")
//    public ResponseEntity<byte[]>downloadTxT(@PathVariable int id){
//        try {
//            byte[] txtData = this.service.downloadCooopeativaTxt(id);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cooperativa_" + id + ".txt");
//            headers.add(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8");
//
//            return new ResponseEntity<>(txtData, headers, HttpStatus.OK);
//        }
//        catch (Exception e){
//            throw new ImportacaoExportacaoException(e.getMessage());
//        }
//    }

    @CurrentUser
    @GetMapping("/buscar")
    public ResponseEntity<CooperativaResponseDTO> buscarCooperativaByToken(){
        String email = UserContextHolder.getUser();
        Cooperativa cooperativa = this.service.buscarCooperativa(email);
        return ResponseEntity.ok(CooperativaMapper.of(cooperativa));
    }
}
