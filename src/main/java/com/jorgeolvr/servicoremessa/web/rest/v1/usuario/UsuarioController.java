package com.jorgeolvr.servicoremessa.web.rest.v1.usuario;

import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/backend/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UsuarioResponse> buscarporId(@PathVariable("id") final Long id) {
        UsuarioResponse usuarioResponse = usuarioService.buscarPorId(id);

        if (Objects.isNull(usuarioResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(usuarioResponse);
        }
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<UsuarioResponse> buscarporCpf(@PathVariable("cpf") final String cpf) {
        UsuarioResponse usuarioResponse = usuarioService.buscarPorCpf(cpf);

        if (Objects.isNull(usuarioResponse.getPessoaFisica())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(usuarioResponse);
        }
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<UsuarioResponse> buscarporCnpj(@PathVariable("cnpj") final String cnpj) {
        UsuarioResponse usuarioResponse = usuarioService.buscarPorCnpj(cnpj);

        if (Objects.isNull(usuarioResponse.getPessoaJuridica())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(usuarioResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> buscarTodos() {
        List<UsuarioResponse> usuarioResponses = usuarioService.buscarTodos();

        if (usuarioResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(usuarioResponses);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest usuarioRequest, HttpServletRequest request) {
        UsuarioResponse usuarioResponse = usuarioService.criar(usuarioRequest);

        if (Objects.nonNull(usuarioResponse)) {
            return ResponseEntity.created(URI.create(request.getRequestURI() + "/" + usuarioResponse.getId())).build();
        } else  {
            return new ResponseEntity<>(usuarioResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable("id") final Long id,
                                                     @RequestBody UsuarioRequest usuarioRequest, HttpServletRequest request) {
        UsuarioResponse usuarioResponse = usuarioService.atualizar(id, usuarioRequest);

        if (Objects.isNull(usuarioResponse)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(usuarioResponse);
        }
    }
}
