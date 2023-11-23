package com.projeto.sprint.projetosprint.controller.usuario.mapper;


import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioTokenDTO;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDTO usuarioCriacaoDTO){
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioCriacaoDTO.email());
        usuario.setSenha(usuarioCriacaoDTO.senha());
        return usuario;
    }

    public static UsuarioTokenDTO of(Usuario usuario, String token){
        UsuarioTokenDTO usuarioTokenDTO = new UsuarioTokenDTO();

        usuarioTokenDTO.setId(usuario.getId());
        usuarioTokenDTO.setEmail(usuario.getEmail());
        usuarioTokenDTO.setTipoUsuario(usuario.getTipoUsuario());
        usuarioTokenDTO.setToken(token);
        return usuarioTokenDTO;
    }
}
