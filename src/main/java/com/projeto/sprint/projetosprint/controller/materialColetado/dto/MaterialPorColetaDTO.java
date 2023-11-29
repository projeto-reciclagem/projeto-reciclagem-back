package com.projeto.sprint.projetosprint.controller.materialColetado.dto;

import com.projeto.sprint.projetosprint.util.chaveValor.ChaveValor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MaterialPorColetaDTO {
    LocalDate data;
    List<ChaveValor> valor;
}
