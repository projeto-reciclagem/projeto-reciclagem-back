package com.projeto.sprint.projetosprint.controller.imagem;

import com.projeto.sprint.projetosprint.service.arquivo.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/arquivo")
public class ImagemController {

    @Autowired
    private ArquivoService imagemService;

    @PostMapping("/upload-imagem")
    public ResponseEntity<String> uploadImagem(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String url = imagemService.enviarImagemParaSupabase(file);
        int i = url.indexOf("/bucket");

        String urlComeco = url.substring(0, i);
        String urlFim = url.substring(i, url.length());

        String urlFinal = urlComeco + "/public" + urlFim;
        return ResponseEntity.ok(urlFinal);
    }
}

