package com.projeto.sprint.projetosprint.service.cooperativa.autenticacao;

import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.condominio.autenticacao.dto.CondominioDetalhesDto;
import com.projeto.sprint.projetosprint.service.cooperativa.autenticacao.dto.CooperativaDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private CooperativaRepository cooperativaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Cooperativa> cooperativaOpt = cooperativaRepository.findByEmail(username);

        if(cooperativaOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado",username));
        }
        return new CooperativaDetalhesDto(cooperativaOpt.get());
    }
}
