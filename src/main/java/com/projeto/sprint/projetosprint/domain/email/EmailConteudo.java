package com.projeto.sprint.projetosprint.domain.email;

import com.projeto.sprint.projetosprint.domain.email.observer.Destinatario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmailConteudo {

    private UUID id = UUID.randomUUID();
    private String titulo;
    private String conteudo;

    List<Destinatario> emailList = new ArrayList<>();

    public EmailConteudo() {}

    public EmailConteudo(String titulo, String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public void inscrever(Destinatario dados){
        this.emailList.add(dados);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public List<Destinatario> getEmailList() {
        return emailList;
    }

    public UUID getId() {
        return id;
    }
}
