package com.projeto.sprint.projetosprint.controller.usuario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MetodoPagamentoAceitoResponseDto {
    private Long id;

    public MetodoPagamentoAceitoResponseDto(Long id) {
        this.id = id;
    }
}
