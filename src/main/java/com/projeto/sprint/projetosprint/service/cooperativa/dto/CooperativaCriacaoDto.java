package com.projeto.sprint.projetosprint.service.cooperativa.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CooperativaCriacaoDto {
    @NotBlank
    @Valid
    private String nome;
    @CNPJ
    private String cnpj;
    @Email(regexp = "@")
    private String email;

    private String senha;

}
