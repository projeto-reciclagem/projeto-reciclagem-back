package com.projeto.sprint.projetosprint.controller.materialColetado;

import com.projeto.sprint.projetosprint.controller.materialColetado.dto.*;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import com.projeto.sprint.projetosprint.exception.ImportacaoExportacaoException;
import com.projeto.sprint.projetosprint.service.material.MaterialColetadoService;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/materiais/coletados")
@RequiredArgsConstructor
public class MaterialColetadoController {
    private final MaterialColetadoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<MaterialColetadoResponseDTO> cadastrarMaterialColetado(@RequestBody MaterialColetadoCadastroDTO dados) {
        return ResponseEntity.ok(MaterialColetadoMapper.of(
                this.service.cadastrarMaterialColetado(dados)
        ));
    }

    @GetMapping("/total-coletado/semana/{id}")
    public ResponseEntity<Double> totalColetadoUltimaSemana(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.totalColetadoUltimaSemana(id)
        );
    }

    @GetMapping("/mais-coletado/{id}")
    public ResponseEntity<MaterialUltimaSemana> buscarMaterialMaisReciclado(@PathVariable int id){
        return ResponseEntity.ok(
          this.service.buscarMaterialMaisReciclado(id)
        );
    }

    @GetMapping("/reciclagem-semanal/{id}")
    public ResponseEntity<List<ChaveValor>> reciclagemSemanal(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.reciclagemSemanal(id)
        );
    }

    @GetMapping("/porcentagem-material/{id}")
    public ResponseEntity<List<ChaveValor>> porcetanegemMaterial(@PathVariable int id) {
        return ResponseEntity.ok(
            this.service.porcentagemPorMaterial(id));
    }

    @GetMapping("/valor-total/mes/{id}")
    public ResponseEntity<Double> valorTotalUltimoMes(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.valorTotalUltimoMes(id)
        );
    }

    @GetMapping("/material-por-coleta/ano/{id}")
    public ResponseEntity<List<MaterialPorColetaDTO>> materialPorColeta(@PathVariable int id){
        return ResponseEntity.ok(
                this.service.kgMaterialPorColeta(id)
        );
    }

    @GetMapping("/valor-recebido/mes/{id}")
    public ResponseEntity<List<ValorRecebidoMesDTO>> valorRecebidoMes(@PathVariable int id){
        return ResponseEntity.ok(
            this.service.valorRecebidoPorMes(id)
        );
    }

    @PostMapping( "/material-por-coleta/ano/{id}/export")
    public ResponseEntity materialColetaAnoExport(@PathVariable int id) {
        try{
            byte[] csvData = this.service.downloadMaterialColetaCsv(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=materiais_coleta_" +
                            LocalDateTime.now()
                                    .toString()
                                    .replace(":", "-")
                                    .replace(".", "-") +
                            ".csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        }
        catch (Exception e){
            throw new ImportacaoExportacaoException(e.getMessage());
        }
    }

    @PostMapping( "/valor-recebido/mes/{id}/export")
    public ResponseEntity valorRecebidoMesExport(@PathVariable int id) {
        try{
            byte[] csvData = this.service.downloadValorRecebidoCsv(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=valor_recebido_" +
                            LocalDateTime.now()
                                    .toString()
                                    .replace(":", "-")
                                    .replace(".", "-") +
                            ".csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        }
        catch (Exception e){
            throw new ImportacaoExportacaoException(e.getMessage());
        }
    }

    @GetMapping("/ultimas-coletas/mes/{id}")
    public ResponseEntity<ColetasUltimoMesDTO> coletasUltimoMes(@PathVariable int id){
        return ResponseEntity.ok(this.service.coletasUltimoMes(id));
    }

    @GetMapping("/quantidade-bags/mes/{id}")
    public ResponseEntity<ColetasUltimoMesDTO> quantidadeBagsUltimoMes(@PathVariable int id){
        return ResponseEntity.ok(this.service.quantidadeBagsUltimoMes(id));
    }

    @GetMapping("/mais-coletados/mes/{id}")
    public ResponseEntity<MaterialColetadoDTO> materialMaisColetado(@PathVariable int id){
        return ResponseEntity.ok(this.service.materialMaisColetado(id));
    }
}
