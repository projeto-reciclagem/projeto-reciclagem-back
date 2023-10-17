package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Material")
@EqualsAndHashCode(of = "idMaterial")
@Validated
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMaterial;

    @NotBlank(message = "O nome do material não pode estar em branco")
    @Size(max = 255, message = "O nome do material não pode exceder 255 caracteres")
    private String nomeMaterial;

    @NotNull(message = "O valor do kg não pode ser nulo")
    @Positive(message = "O valor do kg deve ser um número positivo")
    private Double valorKg;
}


