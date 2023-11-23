package com.projeto.sprint.projetosprint.domain.entity.condominio;

import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Condominio")
@EqualsAndHashCode(of = "id")
public class Condominio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cnpj;

    private Integer qtdMoradores;

    private Integer qtdMoradias;

    private Integer qtdBag;

    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
}
