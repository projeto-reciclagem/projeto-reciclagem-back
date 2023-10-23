package com.projeto.sprint.projetosprint.controller;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.util.ListaObj;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/cooperativas")
public class CooperativaController {
    private CooperativaService service;

    public CooperativaController(CooperativaService service) {
        this.service = service;
    }

    //LISTA TODAS AS COOPERATIVAS
    @GetMapping("/listar-cooperativas")
    public ResponseEntity<List<Cooperativa>> listarCooperativa(){

        List<Cooperativa> cooperativas = this.service.listarCooperativa();

        return cooperativas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cooperativas);
    }

    //BUSCA A COOPERATIVA POR ID
    @GetMapping("/buscar-cooperativa-por-id/{id}")
    public ResponseEntity<Cooperativa> buscarCooperativaPorId(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.buscaCoperativaId(id));
    }

    @PostMapping("/cadastrar-cooperativa")
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
    @PutMapping("/atualizar-cooperativa/{id}")
    public ResponseEntity<Cooperativa> atualizarCooperativa(@PathVariable int id, @RequestBody Cooperativa dados){

        dados.setId(id);
        this.service.atualizarCooperativa(dados);
        return ResponseEntity.ok(dados);
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletar-cooperativa/{id}")
    public ResponseEntity<Void> deletarCooperativaPorId(@PathVariable int id){

        this.service.deletarCooperativa(id);
        return ResponseEntity.noContent().build();
    }

    //GRAVANDO OS DADOS EM UM ARQUIVO CSV
    @PostMapping("/exportar-dados-cooperativa")
    public ResponseEntity<Void> gravaArquivoCsv(@RequestParam String nomeArq){
        FileWriter arq = null; //REPRESENTA O ARQUIVO QUE SERÁ GRAVADO
        Formatter saida = null; //SERÁ USADO PARA ESCREVER NO ARQUIVO
        Boolean deuRuim = false; //VALIDA SE ALGO NÃO DEU CERTO


        List<Cooperativa> listaBanco = this.service.listarCooperativa();

        ListaObj<Cooperativa> lista = (ListaObj<Cooperativa>) listaBanco;

        String dateStamp = LocalDateTime.now()
                .toString()
                .replace(":", "-")
                .replace(".", "-");

        nomeArq +=  "-" + dateStamp + ".csv"; //ACRESCENTA A EXTENSÃO

        //CRIANDO O ARQUIVO || ABRINDO O ARQUIVO
        try {
            arq = new FileWriter(nomeArq); //ABRE O ARQUIVO
            saida = new Formatter(arq); //INSTANCIA O OBJ SAIDA, ASSOCIANDO COM O ARQ
        }
        catch (IOException e){
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // GRAVANDO O ARQUIVO
        try{
            saida.format("%S", "00cooperativa" + dateStamp);

            for (int i = 0; i < lista.getTamanho(); i++){
                Cooperativa c = lista.getElemento(i);
                //GRAVANDO OS DADOS DA COOPERATIVA NO ARQUIVO
                saida.format("%d;%s;%s;%s\n",
                        c.getId(), c.getNome(),c.getCnpj(),c.getEmail());
            }

            saida.format("%d%010d", 01, lista.getTamanho());
        }
        catch (FormatterClosedException err){
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = false;
        }
        finally {
            saida.close();

            try{
                arq.close();
            }
            catch (IOException err){
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = false;
            }

            if (deuRuim){
                System.exit(1);
            }
        }

        return ResponseEntity.noContent().build();
    }
}
