package com.projeto.sprint.projetosprint.api.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collector;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.validaty}")
    private long jwtTokenValidity;

    public Date getUsernameFromToken(String token){

        return  getClaimForToken(token, Claims::getExpiration);
    }

    public  String generateToken(final Authentication authentication){

        final  String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collector.joining(","));

        return Jwts.builder().setSubject(authentication.getName())
                .singnWith(parseSecret()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000)).compact();
    }

    public  <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken (String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private  boolean isTokenExpired (String token){
        Date expirationDate = getExpirationDateFromToken(token);
        return  expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken (String token){
        return  Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private SecretKey parseSecret(){
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }
}
