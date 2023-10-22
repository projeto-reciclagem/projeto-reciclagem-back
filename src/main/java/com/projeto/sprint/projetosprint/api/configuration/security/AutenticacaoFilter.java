package com.projeto.sprint.projetosprint.api.configuration.security;



import com.projeto.sprint.projetosprint.service.usuario.autenticacao.AutenticacaoServiceCoop;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;


public class AutenticacaoFilter extends OncePerRequestFilter {
    private static  final  Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);
    private AutenticacaoServiceCoop autenticacaoServiceCoop;
    //private AutenticacaoServiceCond autenticacaoServiceCond;
    private GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoServiceCoop autenticacaoServiceCoop /*AutenticacaoServiceCond autenticacaoServiceCond*/, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoServiceCoop = autenticacaoServiceCoop;
        //this.autenticacaoServiceCond = autenticacaoServiceCond;
        this.jwtTokenManager = jwtTokenManager;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        String requestTokenHeader = request.getHeader("Authorization");

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")){
            jwtToken =requestTokenHeader.substring(7);

            try{
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (MalformedJwtException erro){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Bad JWT format");
                return;
            }catch (ExpiredJwtException exception){
                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",exception.getClaims().getSubject(),exception.getMessage());

                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s",exception);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            addUsernameInContext(request,username,jwtToken);
        }
        filterChain.doFilter(request, response);
    }
    public void  addUsernameInContext(HttpServletRequest request, String username, String jwtToken){

        UserDetails userDetailsCoop = autenticacaoServiceCoop.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken,userDetailsCoop)){

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationTokenCoop = new UsernamePasswordAuthenticationToken(
                    userDetailsCoop,null,userDetailsCoop.getAuthorities());

            usernamePasswordAuthenticationTokenCoop.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationTokenCoop);
        }

    }
}

