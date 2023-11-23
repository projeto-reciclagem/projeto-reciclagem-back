package com.projeto.sprint.projetosprint.service.email;

import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.domain.entity.email.observer.Destinatario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmailConteudoService {

    private EmailService service;

    public EmailConteudoService(EmailService service) {
        this.service = service;
    }

    private List<EmailConteudo> emailConteudos = new ArrayList<>();

    public void publicarEmail(UUID idEmail){
        EmailConteudo emailConteudo = this.buscarPorIndice(idEmail);
        emailConteudo.getEmailList()
                .forEach(e -> e.enviarEmail(service, emailConteudo));
    }

    public void adicionarDestinatario(UUID idEmail, Destinatario destinatario){
        EmailConteudo email = this.buscarPorIndice(idEmail);
        email.inscrever(destinatario);
    }

    public UUID criarEmail(EmailConteudo emailConteudo){
        this.emailConteudos.add(emailConteudo);
        return emailConteudo.getId();
    }

    public List<EmailConteudo> listarEmail(){return this.emailConteudos;}

    public EmailConteudo buscarPorId(UUID id){return this.buscarPorIndice(id);}
    private EmailConteudo buscarPorIndice(UUID id) {
        return this.emailConteudos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Email não encontrado!")
                );
    }
}
