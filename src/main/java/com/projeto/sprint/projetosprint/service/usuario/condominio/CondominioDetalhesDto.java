package com.projeto.sprint.projetosprint.service.usuario.condominio;

import com.projeto.sprint.projetosprint.domain.condominio.Condominio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CondominioDetalhesDto implements UserDetails {

    private String nome;

    private String cnpj;

    private String email;

    private String senha;

    private Integer qtdMoradores;

    private Integer qtdCasa;

    public CondominioDetalhesDto(Condominio condominio) {
        this.nome = condominio.getNome();
        this.cnpj = condominio.getCnpj();
        this.email = condominio.getEmail();
        this.senha = condominio.getSenha();
        this.qtdMoradores = condominio.getQtdMoradores();
        this.qtdCasa = condominio.getQtdCasa();
    }


    public String getNome(){
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Integer getQtdMoradores() {
        return qtdMoradores;
    }

    public Integer getQtdCasa() {
        return qtdCasa;
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
