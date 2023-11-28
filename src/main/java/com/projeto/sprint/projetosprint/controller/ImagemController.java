package com.projeto.sprint.projetosprint.controller;

import com.projeto.sprint.projetosprint.service.arquivo.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/arquivo")
public class ImagemController {

    @Autowired
    private ArquivoService imagemService;

    @PostMapping("/upload-imagem")
    public ResponseEntity<String> uploadImagem(@RequestParam("file") MultipartFile file) throws IOException {
        String url = imagemService.enviarImagemParaSupabase(file);
        return ResponseEntity.ok("Imagem enviada com sucesso. URL: " + url);
    }
}

