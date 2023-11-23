package com.projeto.sprint.projetosprint.controller.usuario.dto;

import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioTokenDTO {
    private Long id;
    private String email;
    private String token;
    private TipoUsuario tipoUsuario;
}
