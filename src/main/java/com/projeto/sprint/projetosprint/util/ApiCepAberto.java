package com.projeto.sprint.projetosprint.util;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.Value;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ApiCepAberto {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    public Optional<Cep> searchByCep(@NonNull String cep) {
        return cepFromRequest(new Request.Builder()
                .url(String.format("https://www.cepaberto.com/api/v3/cep?cep=%s", cep))
                .addHeader("Authorization", "Token token=292ce57ef419baa933cd17abac7d0d48")
                .build()
        );
    }

    public Optional<Cep> searchByAddress(
            @NonNull String estado,
            @NonNull String cidade,
            String logradouro
    ) {
        return cepFromRequest(
                new Request.Builder()
                        .url(String.format("https://www.cepaberto.com/api/v3/address?estado=%s&cidade=%s&logradouro=%s", estado, cidade, logradouro))
                        .addHeader("Authorization", "Token token=292ce57ef419baa933cd17abac7d0d48")
                        .build()
        );
    }

    private Optional<Cep> cepFromRequest(Request request) {
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return Optional.of(gson.fromJson(response.body().string(), Cep.class));
            }
            return Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Value
    public static class Cep {
        private final String cep;
        private final double altitude;
        private final double latitude;
        private final double longitude;
        private final String bairro;
        private final String logradouro;
        private final Cidade cidade;
        private final Estado estado;

        @Value
        private static class Cidade {
            private final String nome;
            private final Integer ddd;
            private final String ibge;
        }

        @Value
        private static class Estado {
            private final String sigla;
        }
    }

}
