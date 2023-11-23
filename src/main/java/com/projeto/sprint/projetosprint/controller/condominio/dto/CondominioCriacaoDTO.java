package com.projeto.sprint.projetosprint.controller.condominio.dto;

import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class CondominioCriacaoDTO {

    @NotBlank
    public String nome;

    @NotBlank
    @CNPJ
    public String cnpj;

    @Min(value = 1)
    public Integer qtdMoradores;

    @Min(value = 1)
    public Integer qtdMoradias;

    @Min(value = 1)
    public Integer qtdBag;

    @Valid
    public UsuarioCriacaoDTO usuario;
}
