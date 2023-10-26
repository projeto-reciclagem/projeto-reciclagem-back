package com.projeto.sprint.projetosprint.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private static final AntPathRequestMatcher[] URLS_PERMITIDAS_PARA_TODOS = {
            AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/swagger-ui/**"),
            AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/cooperativas/cadastrar-cooperativa"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/condominios/cadastrar-condominio"),
            AntPathRequestMatcher.antMatcher("/h2-console/**"),
            AntPathRequestMatcher.antMatcher("/error/**"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/auth/**"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/cooperativas/exportar-dados-cooperativa/**")
    };

    private static final AntPathRequestMatcher[] URLS_NECESSITAM_PERMICAO = {
            AntPathRequestMatcher.antMatcher("/condominios/**"),
            AntPathRequestMatcher.antMatcher("/cooperativas/**"),
            AntPathRequestMatcher.antMatcher("/agendamentos/**"),
            AntPathRequestMatcher.antMatcher("/materiais/**"),
    };

    SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(URLS_PERMITIDAS_PARA_TODOS).permitAll()
                                .requestMatchers(URLS_NECESSITAM_PERMICAO).hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                        //authorize.requestMatchers(Arrays.toString(URLS_PERMITIDAS)).permitAll();
                        //authorize.requestMatchers(HttpMethod.POST, "/cooperativas").hasRole("COOPERATIVA");
                        //authorize.anyRequest().authenticated();
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
