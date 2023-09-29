package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Entity(name = "Condominio")
@EqualsAndHashCode(of = "id")
public class Condominio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Valid
    private String nome;
    @CNPJ
    private String cnpj;
    @Email(regexp = "@")
    private String email;

    private String senha;
    private Integer qtdMoradores;

    private Integer qtdCasa;
}
