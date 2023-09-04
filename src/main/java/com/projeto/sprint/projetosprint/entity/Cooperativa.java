package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cooperativa")
@Table(name = "cooperativa")
@EqualsAndHashCode(of = "id")
public class Cooperativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cnpj;

    private String email;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "cooperativa_id")
    private Cooperativa matrix;

}
