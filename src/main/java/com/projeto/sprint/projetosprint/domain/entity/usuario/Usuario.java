package com.projeto.sprint.projetosprint.domain.entity.usuario;

import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String senha;

    private String imgUsuario;

    private TipoUsuario tipoUsuario;

    @OneToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;
}
