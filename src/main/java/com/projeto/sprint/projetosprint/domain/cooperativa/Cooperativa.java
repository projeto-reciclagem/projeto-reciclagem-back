package com.projeto.sprint.projetosprint.domain.cooperativa;

import com.projeto.sprint.projetosprint.domain.material.Material;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


    @OneToMany(mappedBy = "material")
    List<Material> material;

}
