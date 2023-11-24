package com.projeto.sprint.projetosprint.controller.condominio.dto;

import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class CondominioCriacaoDTO {

    @NotBlank
    public String nome;

    @NotBlank
    @CNPJ
    public String cnpj;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    @Size(min = 8, max = 40)
    public String senha;

    @Min(value = 1)
    public Integer qtdMoradores;

    @Min(value = 1)
    public Integer qtdMoradias;

    @Min(value = 1)
    public Integer qtdBag;
}
