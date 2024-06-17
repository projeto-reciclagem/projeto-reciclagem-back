package com.projeto.sprint.projetosprint.domain.entity.agenda;

import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime datAgendamento;
    private LocalDateTime datRetirada;
    private Integer qtBag;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "fk_cooperativa")
    private Cooperativa cooperativa;

    @ManyToOne
    @JoinColumn(name = "fk_condominio")
    private Condominio condominio;
}

