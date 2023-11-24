package com.projeto.sprint.projetosprint.service.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailBoasVindas;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.email.EmailConteudoService;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CooperativaService {
    private final CooperativaRepository repository;
    private final UsuarioService usuarioService;
    private EmailConteudoService emailService;


    public CooperativaService(CooperativaRepository repository, UsuarioService usuarioService, EmailConteudoService emailService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    public List<CooperativaResponseDTO> listarCooperativa(){
        return this.repository.findAll().stream().map(CooperativaMapper :: of).toList();
    }

    public Cooperativa buscaCoperativaId(int id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Cooperativa cadastrarCooperativa(CooperativaCriacaoDTO dados){

        UsuarioCriacaoDTO usuarioCriacaoDTO = new UsuarioCriacaoDTO(dados.email, dados.senha);

;        Usuario usuario = usuarioService.cadastrar(usuarioCriacaoDTO);
        usuario.setTipoUsuario(TipoUsuario.COOPERATIVA);

        Cooperativa cooperativa = CooperativaMapper.of(dados);
        cooperativa.setUsuario(usuario);

        UUID idEmail = this.emailService.criarEmail(new EmailConteudo(
                "Seja bem vindo ao ECOsystem, " + dados.getNome() + "!",
                "Esperamos que nossa aplicação auxilie na rotina da Cooperativa " + dados.getNome() + " <br> :)"));

        EmailBoasVindas destinatario = new EmailBoasVindas(
                dados.getNome(), dados.email);

        this.emailService.adicionarDestinatario(
                idEmail, destinatario);

        this.emailService.publicarEmail(idEmail);

        return this.repository.save(cooperativa);
    }

    public Cooperativa atualizarCooperativa(Cooperativa dados, Integer id){

        if(this.repository.existsById(id)){
            dados.setId(id);
            return this.repository.save(dados);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void deletarCooperativa(Integer id){
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public List<Cooperativa> listarCooperativasGenerico(){
        return this.repository.findAll();
    }


}
