package com.projeto.sprint.projetosprint.controller;

import com.projeto.sprint.projetosprint.entity.Condominio;
import com.projeto.sprint.projetosprint.repository.CondominioRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/condominios")
//@AllArgsConstructor
public class CondominioController {

//    @Autowired
    private final CondominioRepository repository;
    public CondominioController(CondominioRepository repository){
        this.repository = repository;
    }



    //Lista dos Condominios


    @GetMapping
    public ResponseEntity<List<Condominio>> listar(){
        List<Condominio> condominio = this.repository.findAll();
        if(condominio.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(condominio);
    }
    //Buscando condominio por ID
    @GetMapping("/buscar-condominio-por-id/{id}")
    public ResponseEntity<Condominio> buscarCondominioPorId(@PathVariable int id){

        Optional<Condominio> registroOpt = this.repository.findById(id);

        if(registroOpt.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200)
                .body(registroOpt.get());
    }

    //Cadastrando Condominio
    @PostMapping("/cadastrarCondominio")
    public ResponseEntity<Condominio> cadastrarCondominio(@RequestBody @Valid Condominio dados){

        Condominio condominioSalva =  this.repository.save(dados);
        return ResponseEntity.status(201).body(condominioSalva);

    }

    //Atualizar os dados do condominio
    @PutMapping("/atualizar-condominio-por-id/{id}")
    public ResponseEntity<Condominio> condominio(@RequestBody @Valid Condominio dados){

        dados.setId(dados.getId());
        this.repository.save(dados);
        return ResponseEntity.status(200).body(dados);
    }

    private void setId(Condominio dados) {
    }

    @DeleteMapping("/deletar-condominio-por-id/{id}")
    public ResponseEntity<Void> deletarCondominioPorId(@PathVariable int id){

        if(!this.repository.existsById(id)){
            return ResponseEntity.status(404).build();
        }

        this.repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }



}
