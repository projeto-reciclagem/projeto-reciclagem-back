package com.projeto.sprint.projetosprint.domain.entity.cooperativa;

import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity(name = "cooperativa")
@EqualsAndHashCode(of = "id")
public class Cooperativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario Usuario;

}
