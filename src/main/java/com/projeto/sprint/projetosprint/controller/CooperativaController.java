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

        if(dados.getNome() == null || dados.getNome().isBlank()){
            return ResponseEntity.status(400).build();
        }

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

    @PutMapping("/atualizarCooperativa")
    public ResponseEntity<Cooperativa> atualizarCooperativa(@RequestParam int indice, @RequestBody Cooperativa dados){

    }
}
