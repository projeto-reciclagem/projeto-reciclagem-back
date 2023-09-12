package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String nome;

    private String cnpj;

    private String email;

    private String senha;

    /*
    @ManyToOne
    @JoinColumn(name = "cooperativa_id")
    private Cooperativa matrix;
     */

}
