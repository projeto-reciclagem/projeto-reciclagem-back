package com.projeto.sprint.projetosprint.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
public class AgendaColeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Cooperativa cooperativa;
    @ManyToOne
    private Condominio condominio;

    private LocalDateTime dataAgendamento;
}
