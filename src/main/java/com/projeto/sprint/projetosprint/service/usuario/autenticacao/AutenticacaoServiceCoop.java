package com.projeto.sprint.projetosprint.service.usuario.autenticacao;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.CooperativaDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class AutenticacaoServiceCoop implements UserDetailsService {
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
