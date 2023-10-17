package com.projeto.sprint.projetosprint.controller;

import com.projeto.sprint.projetosprint.entity.Condominio;
import com.projeto.sprint.projetosprint.repository.CondominioRepository;
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
    public ResponseEntity<?> cadastrarCondominio(@RequestBody @Valid Condominio dados) {
        Condominio condominioSalvo = this.repository.save(dados);
        return ResponseEntity.status(201).body(condominioSalvo);
    }
    //Atualizar os dados do condominio
    @PutMapping("/atualizar-condominio-por-id/{id}")
    public ResponseEntity<?> atualizarCondominio(@PathVariable int id, @RequestBody @Valid Condominio dados) {
        Optional<Condominio> condominioOpt = this.repository.findById(id);
        if (condominioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Condomínio não encontrado");
        }

        Condominio condominioSalvo = condominioOpt.get();
        condominioSalvo.setNome(dados.getNome());
        condominioSalvo.setCnpj(dados.getCnpj());
        condominioSalvo.setEmail(dados.getEmail());
        condominioSalvo.setSenha(dados.getSenha());
        condominioSalvo.setQtdMoradores(dados.getQtdMoradores());
        condominioSalvo.setQtdCasa(dados.getQtdCasa());

        this.repository.save(condominioSalvo);
        return ResponseEntity.status(200).body(condominioSalvo);
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
