package com.projeto.sprint.projetosprint.api.controller;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/cooperativas")
public class CooperativaController {
    private CooperativaService service;

    public CooperativaController(CooperativaService service) {
        this.service = service;
    }

    //LISTA TODAS AS COOPERATIVAS
    @GetMapping("/listarCooperativa")
    public ResponseEntity<List<Cooperativa>> listarCooperativa(){

        List<Cooperativa> cooperativas = this.service.listarCooperativa();

        return cooperativas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cooperativas);
    }

    //BUSCA A COOPERATIVA POR ID
    @GetMapping("/buscarCooperativaPorId/{id}")
    public ResponseEntity<Cooperativa> buscarCooperativaPorId(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.buscaCoperativaId(id));
    }

    @PostMapping("/cadastrarCooperativa")
    public ResponseEntity<Cooperativa> cadastrarCooperativa(@RequestBody Cooperativa dados){

        Cooperativa cooperativaSalva = this.service.cadastrarCooperativa(dados);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cooperativaSalva.getId())
                .toUri();

        return ResponseEntity.created(uri).body(cooperativaSalva);
    }

    //ATUALIZANDO INFORMAÇÕES DA COOPERATIVA
    @PutMapping("/atualizarCooperativaPorId/{id}")
    public ResponseEntity<Cooperativa> atualizarCooperativa(@PathVariable int id, @RequestBody Cooperativa dados){

        dados.setId(id);
        this.service.atualizarCooperativa(dados);
        return ResponseEntity.ok(dados);
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletarCooperativaPorId/{id}")
    public ResponseEntity<Void> deletarCooperativaPorId(@PathVariable int id){

        this.service.deletarCooperativa(id);
        return ResponseEntity.noContent().build();
    }

}
