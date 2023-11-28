package com.projeto.sprint.projetosprint.service.usuario;

import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioLoginDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioTokenDTO;
import com.projeto.sprint.projetosprint.controller.usuario.mapper.UsuarioMapper;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.domain.repository.UsuarioRepository;
import com.projeto.sprint.projetosprint.infra.security.jwt.GerenciadorTokenJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public Usuario cadastrar(UsuarioCriacaoDTO usuarioCriacaoDTO){
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDTO);
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
            novoUsuario.setSenha(senhaCriptografada);
        return repository.save(novoUsuario);
    }

    public UsuarioTokenDTO autenticar(UsuarioLoginDTO usuarioLoginDTO){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDTO.getEmail(), usuarioLoginDTO.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                repository.findByEmail(usuarioLoginDTO.getEmail()).orElseThrow(
                        () -> new RuntimeException("Usuário não encontrado"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Boolean validarEmail(String email){
        return repository.existsByEmail(email);
    }

    public Usuario buscarUsuarioId(Long id){
        if (this.repository.existsById(id)){
            return this.repository.findById(id).get();
        }
        return null;
    }

    public void atualizarUsuario(Usuario usuario){

        this.repository.atualizarInfo(
                usuario.getEmail(),
                passwordEncoder.encode(usuario.getSenha()),
                usuario.getEndereco(),
                usuario.getImgUsuario(),
                usuario.getId()
        );
    }
}
