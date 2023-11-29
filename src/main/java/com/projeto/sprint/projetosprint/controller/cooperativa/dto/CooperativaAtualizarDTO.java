package com.projeto.sprint.projetosprint.controller.cooperativa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class CooperativaAtualizarDTO {
    @NotBlank
    private String nome;

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotBlank
    @Email
    private String email;

    private String ImgUsuario;


    @NotBlank
    @Size(min = 8, max = 40)
    private String senha;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
}
