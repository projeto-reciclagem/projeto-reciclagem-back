package com.projeto.sprint.projetosprint.api.configuration.security;

import com.projeto.sprint.projetosprint.service.usuario.autenticacao.AutenticacaoServiceCond;
import com.projeto.sprint.projetosprint.service.usuario.autenticacao.AutenticacaoServiceCoop;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AutenticacaoProvider implements AuthenticationProvider {

    private final AutenticacaoServiceCond autenticacaoServiceCond;
    private final AutenticacaoServiceCoop autenticacaoServiceCoop;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoProvider(AutenticacaoServiceCond autenticacaoServiceCond, AutenticacaoServiceCoop autenticacaoServiceCoop, PasswordEncoder passwordEncoder) {
        this.autenticacaoServiceCond = autenticacaoServiceCond;
        this.autenticacaoServiceCoop = autenticacaoServiceCoop;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate( final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetailsCoop = this.autenticacaoServiceCoop.loadUserByUsername(username);

        //UserDetails userDetailsCond = this.autenticacaoServiceCond.loadUserByUsername(username);

        if(this.passwordEncoder.matches(password,userDetailsCoop.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetailsCoop, null, userDetailsCoop.getAuthorities());
        /*}else if (this.passwordEncoder.matches(password,userDetailsCond.getPassword())){
            return  new UsernamePasswordAuthenticationToken(userDetailsCond, null,userDetailsCond.getAuthorities());*/
        } else {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
