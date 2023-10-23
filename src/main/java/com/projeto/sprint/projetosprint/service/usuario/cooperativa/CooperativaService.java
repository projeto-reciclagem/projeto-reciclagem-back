package com.projeto.sprint.projetosprint.service.usuario.cooperativa;



import com.projeto.sprint.projetosprint.api.configuration.security.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.dto.CooperativaCriacaoDto;
import com.projeto.sprint.projetosprint.service.usuario.cooperativa.dto.CooperativaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CooperativaService {

    @Autowired
    private CooperativaRepository cooperativaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void criar(CooperativaCriacaoDto cooperativaCriacaoDto){
         Cooperativa novaCooperativa = CooperativaMapper.of(cooperativaCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novaCooperativa.getSenha());
        novaCooperativa.setSenha(senhaCriptografada);
       this.cooperativaRepository.save(novaCooperativa);
    }


    public CooperativaTokenDto autenticar (CooperativaLoginDto cooperativaLoginDto){

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(cooperativaLoginDto.getEmail(), cooperativaLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);


        Cooperativa cooperativaAutenticada =
                cooperativaRepository.findByEmail(cooperativaLoginDto.getEmail()).orElseThrow(
                        ()-> new ResponseStatusException(404,"Email do usuário não cadastrados ",null)
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return CooperativaMapper.of(cooperativaAutenticada,token);
    }
}
