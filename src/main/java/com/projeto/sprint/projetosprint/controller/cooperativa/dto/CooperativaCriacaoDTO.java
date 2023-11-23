package com.projeto.sprint.projetosprint.controller.cooperativa.dto;

import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class CooperativaCriacaoDTO {
    @NotBlank
    public String nome;

    @NotBlank
    @CNPJ
    public String cnpj;

    @Valid
    private UsuarioCriacaoDTO usuario;
}
