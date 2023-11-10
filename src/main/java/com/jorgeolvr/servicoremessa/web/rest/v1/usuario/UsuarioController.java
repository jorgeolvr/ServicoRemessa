package com.jorgeolvr.servicoremessa.web.rest.v1.usuario;

import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/backend/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest usuarioRequest, HttpServletRequest request) {
        UsuarioResponse usuarioResponse = usuarioService.criar(usuarioRequest);
        return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + usuarioResponse.getId())).build();
    }
}
