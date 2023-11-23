package com.projeto.sprint.projetosprint.domain.entity.endereco;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

    private Integer idEndereco;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String complemento;
    private Double latitude;
    private Double longitude;
}
