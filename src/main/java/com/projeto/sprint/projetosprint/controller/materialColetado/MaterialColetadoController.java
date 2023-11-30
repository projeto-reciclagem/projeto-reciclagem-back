package com.projeto.sprint.projetosprint.controller.materialColetado;

import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoCadastroDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialColetadoResponseDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.MaterialPorColetaDTO;
import com.projeto.sprint.projetosprint.controller.materialColetado.dto.ValorRecebidoMesDTO;
import com.projeto.sprint.projetosprint.domain.entity.material.MaterialUltimaSemana;
import com.projeto.sprint.projetosprint.service.material.MaterialColetadoService;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
}
