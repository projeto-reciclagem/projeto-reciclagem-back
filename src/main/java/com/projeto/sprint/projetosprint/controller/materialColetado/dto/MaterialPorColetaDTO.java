package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialPorColetaDTO {
    String data;
    List<ChaveValor> valor;
}
