package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Condominio")
@EqualsAndHashCode(of = "id")
@Validated
public class Condominio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 255, message = "O nome não pode exceder 255 caracteres")
    private String nome;

    @NotBlank(message = "O CNPJ não pode estar em branco")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve ter 14 dígitos numéricos")
    private String cnpj;

    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String senha;

    @Min(value = 0, message = "A quantidade de moradores deve ser maior ou igual a 0")
    private Integer qtdMoradores;

    @Min(value = 0, message = "A quantidade de casas deve ser maior ou igual a 0")
    private Integer qtdCasa;
}