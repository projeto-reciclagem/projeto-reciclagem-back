package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailBoasVindas;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.service.email.EmailConteudoService;
import com.projeto.sprint.projetosprint.util.ListaObj;
import com.projeto.sprint.projetosprint.util.OrdenacaoCnpj;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
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
    @GetMapping("/listar")
    public ResponseEntity<List<CooperativaResponseDTO>> listarCooperativa(){

        List<CooperativaResponseDTO> cooperativas = this.service.listarCooperativa();

        return cooperativas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cooperativas);
    }

    //BUSCA A COOPERATIVA POR ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CooperativaResponseDTO> buscarCooperativaPorId(@PathVariable int id) {
        Cooperativa cooperativa = this.service.buscaCoperativaId(id);
        return ResponseEntity.ok(CooperativaMapper.of(cooperativa));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CooperativaResponseDTO> cadastrarCooperativa(@Valid  @RequestBody CooperativaCriacaoDTO dados){

        Cooperativa cooperativaSalva = this.service.cadastrarCooperativa(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cooperativaSalva.getId()).toUri();

        return ResponseEntity.created(uri).body(CooperativaMapper.of(cooperativaSalva));
    }

    //ATUALIZANDO INFORMAÇÕES DA COOPERATIVA
    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarCooperativa(@PathVariable int id,
                                                    @Valid @RequestBody CooperativaCriacaoDTO dados){
        Cooperativa cooperativa = CooperativaMapper.of(dados);
        this.service.atualizarCooperativa(cooperativa, id);
        return ResponseEntity.noContent().build();
    }

    //DELETANDO UMA COOPERATIVA
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCooperativaPorId(@PathVariable int id){

        this.service.deletarCooperativa(id);
        return ResponseEntity.noContent().build();
    }

    //GRAVANDO OS DADOS EM UM ARQUIVO CSV
    @PostMapping(value = "/exportar-dados/{nomeArq}", produces = "application/csv")
    public ResponseEntity gravaArquivoCsv(@PathVariable String nomeArq) {
        FileWriter arq = null; //REPRESENTA O ARQUIVO QUE SERÁ GRAVADO
        Formatter saida = null; //SERÁ USADO PARA ESCREVER NO ARQUIVO
        Boolean deuRuim = false; //VALIDA SE ALGO NÃO DEU CERTO

        List<Cooperativa> cooperativas = this.service.listarCooperativasGenerico();

        ListaObj<Cooperativa> lista = new ListaObj(cooperativas.size());

        for (Cooperativa c : OrdenacaoCnpj.ordenarPorCnpj(cooperativas)){
            lista.adiciona(c);
        }

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
            for (int i = 0; i < lista.getTamanho(); i++){
                Cooperativa c = lista.getElemento(i);
                //GRAVANDO OS DADOS DA COOPERATIVA NO ARQUIVO
                saida.format("%d;%s;%s\n",
                        c.getId(), c.getNome(),c.getCnpj());
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
            return ResponseEntity.status(200).header("content-disposition", String.format("attachment; filename=\"%s\"", nomeArq)).body(nomeArq);
    }

    /*@GetMapping("/listar-email-cooperativa")
    public ResponseEntity<List<EmailConteudo>> listarEmail(){
        return ResponseEntity.ok(
            this.emailService.listarEmail()
        );
    }
    */

}
