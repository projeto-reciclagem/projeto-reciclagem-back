package com.projeto.sprint.projetosprint.domain.entity.email;

import com.projeto.sprint.projetosprint.domain.entity.email.observer.Destinatario;
import com.projeto.sprint.projetosprint.service.email.EmailService;
import java.util.UUID;

public class EmailBoasVindas implements Destinatario {

    private UUID id = UUID.randomUUID();

    private String nome;
    private String email;
    private static final String EMAIL_EMPRESA = "niltongabrielramos@gmail.com";

    public EmailBoasVindas(){}
    public EmailBoasVindas(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    @Override
    public void enviarEmail(EmailService emailService, EmailConteudo emailConteudo) {
        emailService.sendEmail(EMAIL_EMPRESA, this.email, emailConteudo.getTitulo(), emailConteudo.getConteudo());
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
