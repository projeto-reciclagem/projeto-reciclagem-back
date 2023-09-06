package com.projeto.sprint.projetosprint.controller;


import com.projeto.sprint.projetosprint.entity.Cooperativa;
import jakarta.persistence.Id;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cooperativa")
public class CooperativaController {

    private List<Cooperativa> listCooperativa = new ArrayList<>();

    @PostMapping("/cadastrarCooperativa")
    public ResponseEntity<Cooperativa> cadastrarCooperativa(@RequestBody Cooperativa dados){

        //VALIDAÇÕES DOS CAMPOS QUE SERÃO RECEBIDOS
        if(dados.getNome() == null || dados.getNome().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getCnpj().isBlank() || dados.getCnpj().isEmpty()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getEmail().isEmpty() || dados.getEmail().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getSenha().isEmpty() || dados.getSenha().isBlank()){
            return ResponseEntity.status(400).build();
        }

        //ADICIONANDO UMA NOVA COOPERATIVA
        listCooperativa.add(dados);
        return ResponseEntity.status(201).body(dados);
    }

    @GetMapping("/listarCooperativa")
    public ResponseEntity<List<Cooperativa>> listarTodosUsuarios(){
        if (listCooperativa.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listCooperativa);
    }

    @PutMapping("/atualizarCooperativaPorId")
    public ResponseEntity<Cooperativa> atualizarCooperativa(@RequestParam int indice, @RequestBody Cooperativa dados){

        //VALIDAÇÕES DOS CAMPOS QUE SERÃO RECEBIDOS
        if(indice < 0 || indice >= listCooperativa.size()){
            return ResponseEntity.status(400).build();
        }
        if(dados.getNome() == null || dados.getNome().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getCnpj().isBlank() || dados.getCnpj().isEmpty()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getEmail().isEmpty() || dados.getEmail().isBlank()){
            return ResponseEntity.status(400).build();
        }

        if(dados.getSenha().isEmpty() || dados.getSenha().isBlank()){
            return ResponseEntity.status(400).build();
        }

        //ATUALIZANDO A COOPERATIVA
        listCooperativa.set(indice, dados);
        return ResponseEntity.status(200).body(dados);
    }

    @DeleteMapping("/deletarCooperativaPorId")
    public ResponseEntity<Void> deletarCooperativaPorId(@RequestParam int indice){
        if(indice < 0 || indice >= listCooperativa.size()){
            return ResponseEntity.status(200).build();
        }

        listCooperativa.remove(indice);
        return ResponseEntity.status(200).build();
    }

}
