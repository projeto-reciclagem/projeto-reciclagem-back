package com.projeto.sprint.projetosprint.domain.entity.endereco;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String complemento;
    private String latitude;
    private String longitude;
}
