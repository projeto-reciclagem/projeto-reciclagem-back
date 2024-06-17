package com.projeto.sprint.projetosprint.controller.twoFactorAuth;

import com.projeto.sprint.projetosprint.service.twofactorauth.TwoFactorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/2fa")
public class TwoFactorAuthController {

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @PostMapping("/generate")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        String code = twoFactorAuthService.generateVerificationCode();
        twoFactorAuthService.sendVerificationCode(email, code);
        return ResponseEntity.ok("Código de verificação enviado para " + email);
    }

}
