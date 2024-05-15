package com.projeto.sprint.projetosprint.controller.cooperativa;

import com.projeto.sprint.projetosprint.controller.condominio.dto.CondominioSimpleResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaAtualizarDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaCriacaoDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaResponseDTO;
import com.projeto.sprint.projetosprint.controller.cooperativa.dto.CooperativaSimpleResponseDTO;
import com.projeto.sprint.projetosprint.domain.entity.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.entity.cooperativa.Cooperativa;
import com.projeto.sprint.projetosprint.domain.entity.endereco.Endereco;
import com.projeto.sprint.projetosprint.domain.entity.usuario.Usuario;

public class CooperativaMapper {

    public static Cooperativa of(CooperativaCriacaoDTO cooperativaCriacaoDTO) {
        Cooperativa cooperativa = new Cooperativa();

        cooperativa.setNome(cooperativaCriacaoDTO.nome);
        cooperativa.setCnpj(cooperativaCriacaoDTO.cnpj);
        return cooperativa;
    }

    public static CooperativaResponseDTO of(Cooperativa cooperativa) {
        CooperativaResponseDTO cooperativaResponseDTO = new CooperativaResponseDTO();

        cooperativaResponseDTO.setId(cooperativa.getId());
        cooperativaResponseDTO.setNome(cooperativa.getNome());
        cooperativaResponseDTO.setCnpj(cooperativa.getCnpj());

        if (cooperativa.getUsuario() != null){
            Usuario usuario = cooperativa.getUsuario();
            cooperativaResponseDTO.setEmail(usuario.getEmail());
            cooperativaResponseDTO.setImgUsuario(usuario.getImgUsuario());

            if(usuario.getEndereco() != null){
                Endereco endereco = usuario.getEndereco();
                cooperativaResponseDTO.setCep(endereco.getCep());
                cooperativaResponseDTO.setLogradouro(endereco.getLogradouro());
                cooperativaResponseDTO.setBairro(endereco.getBairro());
                cooperativaResponseDTO.setCidade(endereco.getCidade());
                cooperativaResponseDTO.setComplemento(endereco.getComplemento());
                cooperativaResponseDTO.setNumero(endereco.getNumero());
                cooperativaResponseDTO.setLatitude(endereco.getLatitude());
                cooperativaResponseDTO.setLongitude(endereco.getLongitude());
            }
        }

        return cooperativaResponseDTO;
    }

    public static CooperativaSimpleResponseDTO toSimpleResponseDto(Cooperativa cooperativa) {
        CooperativaSimpleResponseDTO dto = new CooperativaSimpleResponseDTO();

        dto.setNome(cooperativa.getNome());
        dto.setEmail(cooperativa.getUsuario().getEmail());
        dto.setCep(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getCep() : null);
        dto.setLogradouro(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getLogradouro() : null);
        dto.setBairro(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getBairro() : null);
        dto.setCidade(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getCidade() : null);
        dto.setNumero(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getNumero() : null);
        dto.setComplemento(cooperativa.getUsuario().getEndereco() != null ? cooperativa.getUsuario().getEndereco().getComplemento() : null);

        return dto;
    }

    public static Cooperativa of(CooperativaAtualizarDTO cooperativaDTO){
        Cooperativa cooperativa = new Cooperativa();
        Usuario usuario = new Usuario();
        Endereco endereco = new Endereco();

        //GUARDANDO OS DADOS DA COOPERATIVA
        cooperativa.setNome(cooperativaDTO.getNome());
        cooperativa.setCnpj(cooperativaDTO.getCnpj());

        //GUARDANDO OS DADOS DO USUÁRIO
        usuario.setEmail(cooperativaDTO.getEmail());
        usuario.setSenha(cooperativaDTO.getSenha());
        usuario.setImgUsuario(cooperativaDTO.getImgUsuario());

        //GUARDANDO OS DADOS DO ENDERECO
        endereco.setBairro(cooperativaDTO.getBairro());
        endereco.setCep(cooperativaDTO.getCep());
        endereco.setLogradouro(cooperativaDTO.getLogradouro());
        endereco.setCidade(cooperativaDTO.getCidade());
        endereco.setComplemento(cooperativaDTO.getComplemento());
        endereco.setNumero(cooperativaDTO.getNumero());

        usuario.setEndereco(endereco);
        cooperativa.setUsuario(usuario);

        return cooperativa;
    }
}
