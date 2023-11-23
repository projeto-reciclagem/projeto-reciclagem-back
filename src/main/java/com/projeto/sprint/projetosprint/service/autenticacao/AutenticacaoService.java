package com.projeto.sprint.projetosprint.service.autenticacao;


import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioDetalhes;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);
        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("O email %s não foi encontrado",username));
        }

        return new UsuarioDetalhes(usuarioOpt.get());
    }


    public UsuarioDetalhes loadUsuarioDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UsuarioDetalhes) authentication.getPrincipal();
    }
}
