package com.projeto.sprint.projetosprint.service.twofactorauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TwoFactorAuthService {

    @Autowired
    private JavaMailSender mailSender;

    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public void sendVerificationCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Seu código de verificação");
        message.setText("Seu código de verificação é: " + code);
        mailSender.send(message);
    }

}
