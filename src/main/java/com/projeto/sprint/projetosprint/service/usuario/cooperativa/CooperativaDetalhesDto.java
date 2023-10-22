package com.projeto.sprint.projetosprint.service.usuario.cooperativa;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import com.projeto.sprint.projetosprint.domain.cooperativa.Cooperativa;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CooperativaDetalhesDto implements UserDetails {

    private String nome;

    private String cnpj;

    private String email;

    private String senha;


    public CooperativaDetalhesDto(Cooperativa cooperativa){
        this.nome = cooperativa.getNome();
        this.cnpj = cooperativa.getCnpj();
        this.email = cooperativa.getEmail();
        this.senha = cooperativa.getSenha();
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
