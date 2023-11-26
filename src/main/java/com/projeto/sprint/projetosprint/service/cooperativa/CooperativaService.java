package com.projeto.sprint.projetosprint.service.cooperativa;

import com.projeto.sprint.projetosprint.controller.cooperativa.CooperativaMapper;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.controller.endereco.EnderecoMapper;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailBoasVindas;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;
import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.domain.repository.CooperativaRepository;
import com.projeto.sprint.projetosprint.service.email.EmailConteudoService;
import com.projeto.sprint.projetosprint.service.endereco.EnderecoService;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CooperativaService {
    private final CooperativaRepository repository;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private EmailConteudoService emailService;

    public CooperativaService(CooperativaRepository repository, UsuarioService usuarioService, EnderecoService enderecoService, EmailConteudoService emailService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
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

        Usuario usuario = usuarioService.cadastrar(usuarioCriacaoDTO);
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

    public Cooperativa atualizarCooperativa(CooperativaAtualizarDTO dados, Integer id){

        Cooperativa infoCooperativa, cooperativa;
        Usuario usuario, infoUsuario;

        if (this.repository.existsById(id)) {
            infoCooperativa = this.buscaCoperativaId(id);

            cooperativa = CooperativaMapper.of(dados);
            cooperativa.setId(id);

            usuario = cooperativa.getUsuario();
            usuario.setId(infoCooperativa.getUsuario().getId());
            infoUsuario = this.usuarioService.buscarUsuarioId(usuario.getId());

            if (infoUsuario.getEndereco() != null){
                usuario.getEndereco().setId(
                        infoUsuario.getEndereco().getId()
                );
            }

            Endereco endereco = this.enderecoService.cadastrarEndereco(EnderecoMapper.of(usuario.getEndereco()));

            usuario.setEndereco(endereco);
            this.usuarioService.atualizarUsuario(usuario);
            cooperativa.setUsuario(usuario);

            return this.repository.save(cooperativa);
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
