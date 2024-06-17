package com.projeto.sprint.projetosprint.util.annotations.currentUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CurrentUserAspect {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final HttpServletRequest request;

    @Before("@annotation(CurrentUser)")
    public void before() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret.getBytes())
                            .parseClaimsJws(token)
                            .getBody();
                    String username = claims.getSubject();
                    UserContextHolder.setUser(username);
                    break;
                }
            }
        }
    }

    @After("@annotation(CurrentUser)")
    public void after() {
        UserContextHolder.clear();
    }
}
