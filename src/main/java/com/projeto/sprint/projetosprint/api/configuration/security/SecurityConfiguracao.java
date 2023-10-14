package com.projeto.sprint.projetosprint.api.configuration.security;

import com.projeto.sprint.projetosprint.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.service.condominio.autenticacao.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguracao {
    private static final String ORIGENS_PERMITIDAS = "*";

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private  AutenticacaoEntryPoint autenticacaoEntryPoint;

    private  final AntPathMatcher[] URLS_PERMITIDAS = {
            new AntPathMatcher("/swagger-ui/**"),
            new AntPathMatcher("/swagger-ui.html"),
            new AntPathMatcher("/swagger-resources"),
            new AntPathMatcher("/swagger-resources/**"),
            new AntPathMatcher("/configuration/ui"),
            new AntPathMatcher("/configuration/security"),
            new AntPathMatcher("/api/public/**"),
            new AntPathMatcher("/api/public/authenticate"),
            new AntPathMatcher("/webjars/**"),
            new AntPathMatcher("/v3/api-docs/**"),
            new AntPathMatcher("/actuator/*"),
            new AntPathMatcher("/usuarios/login/**"),
            new AntPathMatcher("/h2-console/**"),
            new AntPathMatcher("/error/**")
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.headers()
                .frameOptions().disable()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(autenticacaoEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http)throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new AutenticacaoProvider(autenticacaoService,passwordEnconder()));
        return  authenticationManagerBuilder.build();
    }

    @Bean
    public  AutenticacaoEntryPoint jwtAuthenticationFilterBean(){
        return  new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationEntryPointBean(){
        return  new AutenticacaoFilter(autenticacaoService, jwtAuthenticationFilterBean());
    }

    @Bean
    public GerenciadorTokenJwt jwtAuthenticationUtilBean(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEnconder passwordEnconder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.applyPermitDefaultValues();
        configuracao.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.TRACE.name()));
        configuracao.setExposedHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);

        return origem;
    }
}
