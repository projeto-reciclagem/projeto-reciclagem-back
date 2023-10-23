package com.projeto.sprint.projetosprint.domain.email.observer;

import com.projeto.sprint.projetosprint.domain.email.EmailConteudo;
import com.projeto.sprint.projetosprint.service.email.EmailService;

public interface Destinatario {
    void enviarEmail(EmailService emailService, EmailConteudo emailConteudo);
}
