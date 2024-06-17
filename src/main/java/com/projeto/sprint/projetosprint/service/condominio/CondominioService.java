package com.projeto.sprint.projetosprint.service.condominio;

import com.projeto.sprint.projetosprint.controller.condominio.CondominioMapper;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.controller.endereco.EnderecoMapper;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.infra.security.jwt.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.service.endereco.EnderecoService;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CondominioService {

    private final CondominioRepository repository;

    private final UsuarioService usuarioService;

//    private final EmailConteudoService emailService;

    private  final EnderecoService enderecoService;

    private final GerenciadorTokenJwt tokenJwt;


    public List<CondominioResponseDTO> listarCondominio(){
        return this.repository
                .findAll()
                .stream()
                .map(CondominioMapper:: of).toList();
    }

    public Condominio buscaCondominioId(Integer id){
        return this.repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Campo id inválido")
        );
    }

    public Condominio cadastrarCondominio(CondominioCriacaoDTO dados) {

        UsuarioCriacaoDTO usuarioCriacaoDTO  = new UsuarioCriacaoDTO(dados.email, dados.senha);

        if(this.usuarioService.validarEmail(usuarioCriacaoDTO.email())){
            throw new EntidadeDuplicadaException("Email já cadastrado");
        }

        Usuario usuario = usuarioService.cadastrar(usuarioCriacaoDTO);
        usuario.setTipoUsuario(TipoUsuario.CONDOMINIO);

        Condominio condominio = CondominioMapper.of(dados);
        condominio.setUsuario(usuario);

//        UUID idEmail = this.emailService.criarEmail(new EmailConteudo(
//                "Seja bem vindo ao ECOsystem, " + dados.getNome() + "!",
//                "Esperamos que nossa aplicação auxilie na rotina de coleta do seu Condominio " + dados.getNome() + " <br> :)"));
//
//        EmailBoasVindas destinatario = new EmailBoasVindas(
//                dados.getNome(), dados.email);
//
//        this.emailService.adicionarDestinatario(
//                idEmail, destinatario);
//        this.emailService.publicarEmail(idEmail);


        return this.repository.save(condominio);
    }

    public Condominio atualizarCondominio(CondominioAtualizarDTO dados, int id){

        Condominio infoCondominio, condominio;
        Usuario usuario, infoUsuario;

        if(this.repository.existsById(id)){
            infoCondominio = this.buscaCondominioId(id);

            condominio = CondominioMapper.of(dados);
            condominio.setId(id);

            usuario = condominio.getUsuario();
            usuario.setId(infoCondominio.getUsuario().getId());
            infoUsuario = this.usuarioService.buscarUsuarioId(usuario.getId());

            if (usuario.getSenha() == null){
                usuario.setSenha(infoUsuario.getSenha());
            }

            if (infoUsuario.getEndereco() != null){
                usuario.getEndereco().setId(
                        infoUsuario.getEndereco().getId()
                );
            }

            usuario.setEndereco(
                    this.enderecoService.cadastrarEndereco(
                            EnderecoMapper.of(usuario.getEndereco()))
            );

            this.usuarioService.atualizarUsuario(usuario);
            condominio.setUsuario(usuario);

            return this.repository.save(condominio);
        }

        throw new EntidadeNaoEncontradaException("Campo id inválido");
    }

    public void deletarCondominio(Condominio condominio){
        if(this.repository.existsById(condominio.getId())){
            this.repository.deleteById(condominio.getId());
        }
        else{
            throw new EntidadeNaoEncontradaException("Campo id inválido");
        }
    }

    public Condominio buscarCondominio(String email) {
        return this.repository.findByUsuarioEmail(email).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Email inválido"
                )
        );
    }
}