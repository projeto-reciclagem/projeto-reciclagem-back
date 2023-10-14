package com.projeto.sprint.projetosprint.api.controller;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.service.condominio.autenticacao.dto.CondominioLoginDto;
import com.projeto.sprint.projetosprint.service.condominio.autenticacao.dto.CondominioTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/condominios")
//@AllArgsConstructor
public class CondominioController {

//    @Autowired
    private CondominioRepository repository;

    private CondominioService condominioService;
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
    public ResponseEntity<Condominio> cadastrarCondominio(@RequestBody Condominio dados){

        //VALIDAÇÕES DOS CAMPOS
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
        repository.save(dados);
        return ResponseEntity.status(201).body(dados);
    }

    //Atualizar os dados do condominio
    @PutMapping("/atualizar-condominio-por-id/{id}")
    public ResponseEntity<Condominio> condominio(@RequestBody @Valid Condominio dados){

        dados.setId(dados.getId());
        this.repository.save(dados);
        return ResponseEntity.status(200).body(dados);
    }
    @PostMapping("/login")
    public ResponseEntity<CondominioTokenDto> login(@RequestBody CondominioLoginDto condominioLoginDto){
        CondominioTokenDto condominioTokenDto = this.condominioService.autenticar(condominioLoginDto);
        return ResponseEntity.status(200).body(condominioTokenDto);
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
