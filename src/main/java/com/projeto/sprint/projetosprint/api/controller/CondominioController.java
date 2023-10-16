package com.projeto.sprint.projetosprint.api.controller;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/condominios")
public class CondominioController {

    private CondominioService service;

    public CondominioController(CondominioService service) {
        this.service = service;
    }

    //Lista condominio
    @GetMapping("/listarCondominio")
    public ResponseEntity<List<Condominio>> listar(){
        List<Condominio> condominio = this.service.listarCondominio();

        return condominio.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(condominio);
    }

    //Buscando condominio por ID
    @GetMapping("/buscarCondominioPorId/{id}")
    public ResponseEntity<Condominio> buscarCondominioPorId(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.buscaCondominioId(id)
        );
    }

    //Cadastrando Condominio
    @PostMapping("/cadastrarCondominio")
    public ResponseEntity<Condominio> cadastrarCondominio(@RequestBody Condominio dados){

        Condominio condominioSalvo =  this.service.cadastrarCondominio(dados);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(condominioSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(condominioSalvo);
    }

    //Atualizar os dados do condominio
    @PutMapping("/atualizarCondominioPorId/{id}")
    public ResponseEntity<Condominio> condominio(@RequestBody @Valid Condominio dados){

        dados.setId(dados.getId());
        this.service.atualizarCondominio(dados);
        return ResponseEntity.ok(dados);
    }

    @DeleteMapping("/deletarCondominioPorId/{id}")
    public ResponseEntity<Void> deletarCondominioPorId(@PathVariable int id){

        this.service.deletarCondominio(id);
        return ResponseEntity.noContent().build();
    }
}
