package com.projeto.sprint.projetosprint.domain.entity.email.observer;

import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.service.email.EmailService;

public interface Destinatario {
    void enviarEmail(EmailService emailService, EmailConteudo emailConteudo);
}
