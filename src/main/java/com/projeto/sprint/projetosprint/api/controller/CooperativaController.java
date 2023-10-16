package com.projeto.sprint.projetosprint.api.controller;

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

    //GRAVANDO OS DADOS EM UM ARQUIVO CSV
    @PostMapping("/gravarArquivoCsv")
    public ResponseEntity<Void> gravaArquivoCsv(@RequestParam String nomeArq){
        FileWriter arq = null; //REPRESENTA O ARQUIVO QUE SERÁ GRAVADO
        Formatter saida = null; //SERÁ USADO PARA ESCREVER NO ARQUIVO
        Boolean deuRuim = false; //VALIDA SE ALGO NÃO DEU CERTO


        List<Cooperativa> lista = this.service.listarCooperativa();

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
            for (Cooperativa c : lista){
                //GRAVANDO OS DADOS DA COOPERATIVA NO ARQUIVO
                saida.format("%d;%s;%s;%s\n",
                        c.getId(), c.getNome(),c.getCnpj(),c.getEmail());
            }

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


    public static void leExibeArquivoCsv(String nomeAr) {
        FileReader arq = null; //REPRESENTA O ARQUIVO A SER LIDO
        Scanner entrada = null;
        boolean deuRuim = false;

        //ACRESCENTA A EXTENÇÃO .csv
        nomeAr += ".csv";

        //ABRINDO O ARQUIVO
        try {
            arq = new FileReader(nomeAr);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        }
        catch (FileNotFoundException error){
            System.out.println("Arquivo inexistente");
            System.exit(1);
        }

        //LENDO CADA LINHA DO ARQUIVO
        try {
            System.out.printf("%-4S %-15S %-9S %4S\n", "id", "nome", "porte", "peso");

            //hasNext() RETORNA TRUE ENQUANTO TEM LINHAS PARA LER NO ARQUIVO
            while (entrada.hasNext()){
                int id = entrada.nextInt();
                String nome = entrada.next();
                String porte = entrada.next();
                Double peso = entrada.nextDouble();

                System.out.printf("%04d %-15s %-9s %4.1f\n", id, nome, porte, peso);
            }
        }
        catch (NoSuchElementException error){
            System.out.println("Arquivo com problemas");
            error.printStackTrace();
        }
        catch (IllegalStateException error){
            System.out.println("Erro na leitura do arquivo");
            error.printStackTrace();
            deuRuim = true;
        }
        finally {
            entrada.close();

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
    }

}
