package com.projeto.sprint.projetosprint.controller.condominio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class CondominioAtualizarDTO {
    public String nome;

    public String cnpj;
    public String email;

    public String ImgUsuario;

    public String senha;

    public Integer qtdMoradores;

    public Integer qtdMoradias;

    public Integer qtdBag;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
    private String numero;
}
