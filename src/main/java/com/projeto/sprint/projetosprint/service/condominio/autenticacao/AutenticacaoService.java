package com.projeto.sprint.projetosprint.service.condominio.autenticacao;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.service.condominio.autenticacao.dto.CondominioDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private CondominioRepository condominioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Condominio> condominioOpt = condominioRepository.findByEmail(username);

        if(condominioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado",username));
        }
        return new CondominioDetalhesDto(condominioOpt.get());
    }
}
