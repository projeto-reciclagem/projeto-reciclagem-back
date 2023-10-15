package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Entity(name = "cooperativa")
@EqualsAndHashCode(of = "id")
public class Cooperativa {
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

    @Min(1)
    private String senha;

}
