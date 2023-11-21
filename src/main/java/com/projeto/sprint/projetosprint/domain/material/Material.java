package com.projeto.sprint.projetosprint.domain.material;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @ManyToOne
    private Cooperativa cooperativa;

}


