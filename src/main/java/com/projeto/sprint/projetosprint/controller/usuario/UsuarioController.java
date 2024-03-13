package com.projeto.sprint.projetosprint.controller.usuario;

import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioLoginDTO;
import com.projeto.sprint.projetosprint.controller.usuario.dto.UsuarioTokenDTO;
import com.projeto.sprint.projetosprint.service.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDTO> login(@RequestBody UsuarioLoginDTO usuarioLoginDTO, HttpServletResponse response){
        UsuarioTokenDTO usuarioToken = usuarioService.autenticar(usuarioLoginDTO);

        ResponseCookie cookie = ResponseCookie.from("auth", usuarioToken.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(usuarioToken);
    }
}
