package com.projeto.sprint.projetosprint.infra.security;


import com.projeto.sprint.projetosprint.infra.security.jwt.GerenciadorTokenJwt;
import com.projeto.sprint.projetosprint.service.autenticacao.AutenticacaoService;
import com.projeto.sprint.projetosprint.util.MutableHttpServletRequest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;

    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
           for (Cookie cookie : cookies) {
               logger.debug(cookie.getName() + " = " + cookie.getValue());
               if (cookie.getName().equalsIgnoreCase("auth")) {
                   mutableRequest.putHeader("Authorization", URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
                   jwtToken = mutableRequest.getHeader("Authorization");
               }
           }

           try {
               username = jwtTokenManager.getUsernameFromToken(jwtToken);
           } catch (MalformedJwtException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Bad JWT format");
                return;
           } catch (ExpiredJwtException exception) {
               LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
                       exception.getClaims().getSubject(), exception.getMessage());
               LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);

               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               return;
           }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            addUsernameInContext(request, username, jwtToken);
        }

        filterChain.doFilter(mutableRequest, response);
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {

        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}