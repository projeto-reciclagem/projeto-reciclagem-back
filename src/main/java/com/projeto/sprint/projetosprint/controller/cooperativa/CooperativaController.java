package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailBoasVindas;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.domain.entity.material.Material;
import com.projeto.sprint.projetosprint.service.cooperativa.CooperativaService;
import com.projeto.sprint.projetosprint.service.email.EmailConteudoService;
import com.projeto.sprint.projetosprint.service.material.MaterialService;
import com.projeto.sprint.projetosprint.util.ListaObj;
import com.projeto.sprint.projetosprint.util.OrdenacaoCnpj;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/cooperativas")
public class CooperativaController {
    private CooperativaService service;

    private MaterialService serviceMaterial;

    public CooperativaController(CooperativaService service, MaterialService serviceMaterial) {
        this.service = service;
        this.serviceMaterial = serviceMaterial;
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

    @GetMapping("/download-txt/{id}")
    public ResponseEntity<byte[]>downloadTxT(@PathVariable Integer id){
        Cooperativa cooperativa = service.buscaCoperativaId(id);

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
             String header = "00COOPERATIVA";
             header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

             outputStreamWriter.write(header+"\n");

             String corpo = "02";
             corpo += String.format("%-25.25s", cooperativa.getNome());
             corpo += String.format("%-16.16s", cooperativa.getCnpj());
             corpo += String.format("%-30.30s", cooperativa.getUsuario().getEmail());

             outputStreamWriter.write(corpo+"\n");
             for (Material m : cooperativa.getMateriais()) {
                 String corpo2 = "03";
                 corpo2 += String.format("%-17.17s",m.getNome());
                 corpo2 += String.format("%3.1f",m.getValorKg());

                 outputStreamWriter.write(corpo2+"\n");
             }
             String trailer = "01";
             trailer += String.format("%10d",cooperativa.getMateriais().size()+1);

             outputStreamWriter.write(trailer);
             outputStreamWriter.close();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("content-disposition","attachament; filename=Registro-de-Coleta.txt");
            httpHeaders.add("content-type","text/csv; charset=UTF-8");
            return ResponseEntity.status(200).headers(httpHeaders).body(byteArrayOutputStream.toByteArray());
    }
        catch (IOException erro){
            throw  new RuntimeException(erro);
        }
    }

     @PostMapping("/upload/{id}")
    public ResponseEntity<List<Material>> uploadTxt(@RequestParam MultipartFile file, @PathVariable Integer id){
         List materiais = this.serviceMaterial.uploadTxT(file,id);
        return ResponseEntity.ok(materiais);
        
    }

    /*@GetMapping("/listar-email-cooperativa")
    public ResponseEntity<List<EmailConteudo>> listarEmail(){
        return ResponseEntity.ok(
            this.emailService.listarEmail()
        );
    }
    */

}
