package com.projeto.sprint.projetosprint.service.usuario.condominio;

import com.projeto.sprint.projetosprint.api.configuration.security.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.service.usuario.condominio.dto.CondominioCriacaoDto;
import com.projeto.sprint.projetosprint.service.usuario.condominio.dto.CondominioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CondominioService {

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CondominioRepository condominioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void criar(CondominioCriacaoDto condominioCriacaoDto){
        final Condominio novoConominio = CondominioMapper.of(condominioCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoConominio.getSenha());
        novoConominio.setSenha(senhaCriptografada);

        condominioRepository.save(novoConominio);
    }

    public CondominioTokenDto autenticar(CondominioLoginDto condominioLoginDto){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                condominioLoginDto.getEmail(), condominioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Condominio condominioAutenticado =
                 condominioRepository.findByEmail(condominioLoginDto.getEmail())
                         .orElseThrow(
                                 ()-> new ResponseStatusException(404,"Email do Condominio não cadastrado ", null)
                         );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return CondominioMapper.of(condominioAutenticado,token);

    }
}
