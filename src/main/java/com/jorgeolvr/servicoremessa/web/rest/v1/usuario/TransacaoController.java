package com.jorgeolvr.servicoremessa.web.rest.v1.usuario;

import com.jorgeolvr.servicoremessa.dto.transacao.request.TransacaoRequest;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import com.jorgeolvr.servicoremessa.service.transacao.TransacaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/backend/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<TransacaoResponse> buscarPorId(@PathVariable("id") final Long id) {
        TransacaoResponse transacaoResponse = transacaoService.buscarPorId(id);

        if (Objects.isNull(transacaoResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(transacaoResponse);
        }
    }

    @GetMapping(value = "/buscar-por-tipo-movimentacao/{tipoMovimentacao}")
    public ResponseEntity<List<TransacaoResponse>> buscarPorTipoMovimentacao(@PathVariable("tipoMovimentacao") final TipoMovimentacao tipoMovimentacao) {
        List<TransacaoResponse> transacaoResponses = transacaoService.buscarPorTipoMovimentacao(tipoMovimentacao);

        if (transacaoResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(transacaoResponses);
        }
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> buscarTodos() {
        List<TransacaoResponse> transacaoResponses = transacaoService.buscarTodos();

        if (transacaoResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(transacaoResponses);
        }
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> transacao(@Valid @RequestBody TransacaoRequest transacaoRequest) throws Throwable {
        TransacaoResponse usuarioResponse = transacaoService.realizarTransacao(transacaoRequest);

        if (Objects.isNull(usuarioResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(usuarioResponse);
        }
    }
}
