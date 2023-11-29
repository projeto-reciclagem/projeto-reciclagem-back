package com.projeto.sprint.projetosprint.service.arquivo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ArquivoService {
    @Value("${supabase.storage.url}")
    private String supabaseStorageUrl;

    @Value("${supabase.storage.apiKey}")
    private String supabaseApiKey;

    public String enviarImagemParaSupabase(MultipartFile file) throws IOException {

        String storageEndpoint = supabaseStorageUrl + "/" + file.getOriginalFilename()
                .replace(" ", "");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(supabaseApiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(file.getBytes(), headers));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(storageEndpoint, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return storageEndpoint; // Retorna a URL do arquivo no Supabase Storage
        } else {
            throw new RuntimeException("Erro ao enviar a imagem para o Supabase.");
        }
    }
}
