package com.projeto.sprint.projetosprint.controller.condominio;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.service.condominio.CondominioService;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.CurrentUser;
import com.projeto.sprint.projetosprint.util.annotations.currentUser.UserContextHolder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/condominios")
@RequiredArgsConstructor
public class CondominioController {

    private final CondominioService service;

    //Lista condominio
    @GetMapping("/listar")
    public ResponseEntity<List<CondominioResponseDTO>> listar(){
        List<CondominioResponseDTO> condominio = this.service.listarCondominio();

        return condominio.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(condominio);
    }

    //Buscando condominio por ID
    @CurrentUser
    @GetMapping("/buscar")
    public ResponseEntity<CondominioResponseDTO> buscarCondominioPorId() {
        String email = UserContextHolder.getUser();
        Condominio condominio = this.service.buscarCondominio(email);
        return ResponseEntity.ok(CondominioMapper.of(condominio));
    }

    //Cadastrando Condominio
    @PostMapping("/cadastrar")
    public ResponseEntity<CondominioResponseDTO> cadastrarCondominio(@RequestBody @Valid CondominioCriacaoDTO dados){

        Condominio condominioSalvo =  this.service.cadastrarCondominio(dados);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(condominioSalvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(CondominioMapper.of(condominioSalvo));
    }

    //Atualizar os dados do condominio
    @CurrentUser
    @PutMapping("/atualizar")
    public ResponseEntity<CondominioResponseDTO> condominio(@Valid @RequestBody CondominioAtualizarDTO dados){
        String email = UserContextHolder.getUser();
        Condominio condominioAtual = this.service.buscarCondominio(email);

        Condominio condominio = this.service.atualizarCondominio(dados, condominioAtual.getId());
        return ResponseEntity.ok(CondominioMapper.of(condominio));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarCondominioPorId(@RequestHeader(HttpHeaders.COOKIE) String auth){
        Condominio condominio = this.service.buscarCondominio(auth.replace("auth=", ""));
        this.service.deletarCondominio(condominio);
        return ResponseEntity.noContent().build();
    }
}
