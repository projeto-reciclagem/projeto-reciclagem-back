package com.projeto.sprint.projetosprint.domain.cooperativa;

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

    private String cnpj;

    private String email;

    @Size(min = 4)
    private String senha;

}
