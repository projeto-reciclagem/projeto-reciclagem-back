package com.projeto.sprint.projetosprint.controller.condominio;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;

public class CondominioMapper {
    public static Condominio of(CondominioCriacaoDTO condominioCriacaoDTO){
        Condominio condominio = new Condominio();

        condominio.setNome(condominioCriacaoDTO.nome);
        condominio.setCnpj(condominioCriacaoDTO.cnpj);
        condominio.setQtdMoradores(condominioCriacaoDTO.qtdMoradores);
        condominio.setQtdMoradias(condominioCriacaoDTO.qtdMoradias);
        condominio.setQtdBag(condominioCriacaoDTO.qtdBag);
        return condominio;
    }

    public static CondominioResponseDTO of(Condominio condominio){
        CondominioResponseDTO condominioResponseDTO = new CondominioResponseDTO();

        condominioResponseDTO.setId(condominio.getId());
        condominioResponseDTO.setNome(condominio.getNome());
        condominioResponseDTO.setCnpj(condominio.getCnpj());
        condominioResponseDTO.setQtdMoradores(condominio.getQtdMoradores());
        condominioResponseDTO.setQtdMoradias(condominio.getQtdMoradias());
        condominioResponseDTO.setQtdBag(condominio.getQtdBag());

        if (condominio.getUsuario() != null){
            Usuario usuario = condominio.getUsuario();
            condominioResponseDTO.setEmail(usuario.getEmail());
            condominioResponseDTO.setImgUsuario(usuario.getImgUsuario());

            if (usuario.getEndereco() != null){
                Endereco endereco = condominio.getUsuario().getEndereco();
                condominioResponseDTO.setCep(endereco.getCep());
                condominioResponseDTO.setLogradouro(endereco.getLogradouro());
                condominioResponseDTO.setBairro(endereco.getBairro());
                condominioResponseDTO.setCidade(endereco.getCidade());
                condominioResponseDTO.setComplemento(endereco.getComplemento());
                condominioResponseDTO.setNumero(endereco.getNumero());
                condominioResponseDTO.setLatitude(endereco.getLatitude());
                condominioResponseDTO.setLongitude(endereco.getLongitude());
            }
        }

        return condominioResponseDTO;
    }

    public static Condominio of(CondominioAtualizarDTO condominioDTO){
        Condominio condominio = new Condominio();
        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();

        //CONDOMINIO
        condominio.setNome(condominioDTO.nome);
        condominio.setCnpj(condominioDTO.cnpj);
        condominio.setQtdMoradores(condominioDTO.qtdMoradores);
        condominio.setQtdMoradias(condominioDTO.qtdMoradias);
        condominio.setQtdBag(condominioDTO.qtdBag);

        //USUARIO
        usuario.setEmail(condominioDTO.email);
        usuario.setSenha(condominioDTO.senha);
        usuario.setImgUsuario(condominioDTO.getImgUsuario());

        //ENDERECO
        endereco.setBairro(condominioDTO.getBairro());
        endereco.setCep(condominioDTO.getCep());
        endereco.setLogradouro(condominioDTO.getLogradouro());
        endereco.setCidade(condominioDTO.getCidade());
        endereco.setComplemento(condominioDTO.getComplemento());
        endereco.setNumero(condominioDTO.getNumero());

        usuario.setEndereco(endereco);
        condominio.setUsuario(usuario);

        return condominio;
    }
}
