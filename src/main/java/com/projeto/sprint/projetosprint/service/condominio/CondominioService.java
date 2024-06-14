package com.projeto.sprint.projetosprint.service.condominio;

import com.projeto.sprint.projetosprint.controller.condominio.CondominioMapper;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.controller.endereco.EnderecoMapper;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioCriacaoDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailBoasVindas;
import com.projeto.sprint.projetosprint.domain.entity.email.EmailConteudo;
import com.projeto.sprint.projetosprint.domain.entity.usuario.TipoUsuario;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;
import com.projeto.sprint.projetosprint.domain.repository.CondominioRepository;
import com.projeto.sprint.projetosprint.exception.EntidadeDuplicadaException;
import com.projeto.sprint.projetosprint.exception.EntidadeNaoEncontradaException;
import com.projeto.sprint.projetosprint.infra.security.jwt.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.service.email.EmailConteudoService;
import com.projeto.sprint.projetosprint.service.endereco.EnderecoService;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CondominioService {

    private final CondominioRepository repository;

    private final UsuarioService usuarioService;

    private final EmailConteudoService emailService;

    private  final EnderecoService enderecoService;

    private final GerenciadorTokenJwt tokenJwt;


    public List<CondominioResponseDTO> listarCondominio(){
        return this.repository
                .findAll()
                .stream()
                .map(CondominioMapper:: of).toList();
    }

    public Condominio buscarCondominioId(Integer id){
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

        UUID idEmail = this.emailService.criarEmail(new EmailConteudo(
                "Seja bem vindo ao ECOsystem, " + dados.getNome() + "!",
                "Esperamos que nossa aplicação auxilie na rotina de coleta do seu Condominio " + dados.getNome() + " <br> :)"));

        EmailBoasVindas destinatario = new EmailBoasVindas(
                dados.getNome(), dados.email);

        this.emailService.adicionarDestinatario(
                idEmail, destinatario);
        this.emailService.publicarEmail(idEmail);


        return this.repository.save(condominio);
    }

    public Condominio atualizarCondominio(CondominioAtualizarDTO dados, Condominio condominioAtual){

        Condominio infoCondominio;
        Usuario usuario, infoUsuario;

        if(condominioAtual != null){

            usuario = condominioAtual.getUsuario();
//            infoUsuario = this.usuarioService.buscarUsuarioId(usuario.getId());

            if (dados.email != null){
                usuario.setEmail(dados.email);
            }

            if (dados.senha != null){
                usuario.setSenha(dados.senha);
            }

            if (dados.nome != null){
                condominioAtual.setNome(dados.nome);
            }

            if (dados.cnpj != null){
                condominioAtual.setCnpj(dados.cnpj);
            }

//            if (infoUsuario.getEndereco() != null){
//                usuario.getEndereco().setId(
//                        infoUsuario.getEndereco().getId()
//                );
//            }
//
//            usuario.setEndereco(
//                    this.enderecoService.cadastrarEndereco(
//                            EnderecoMapper.of(usuario.getEndereco()))
//            );

            this.usuarioService.atualizarUsuario(usuario);
            condominioAtual.setUsuario(usuario);

            return this.repository.save(condominioAtual);
        }

        throw new EntidadeNaoEncontradaException("Condominio não encontrado");
    }

    public void deletarCondominio(Condominio condominio){
        if(this.repository.existsById(condominio.getId())){
            this.repository.deleteById(condominio.getId());
        }
        else{
            throw new EntidadeNaoEncontradaException("Campo id inválido");
        }
    }

    public Condominio buscarCondominio(String auth) {
        String email = tokenJwt.getUsernameFromToken(auth);

        return this.repository.findByUsuarioEmail(email).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        "Email inválido"
                )
        );
    }
}