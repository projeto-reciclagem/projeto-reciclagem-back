package com.projeto.sprint.projetosprint.controller.agendamento.dto;

import lombok.Data;

@Data
public class AgendaResponse {
    private int id;
    private String condominio;
    private String responsavel;
    private String data;
    private int bags;
    private Double valor;
    private String horaColeta;
    private String status;
    private String endereco;
}
