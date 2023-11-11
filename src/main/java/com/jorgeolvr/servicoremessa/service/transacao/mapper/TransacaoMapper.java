package com.jorgeolvr.servicoremessa.service.transacao.mapper;

import com.jorgeolvr.servicoremessa.domain.Transacao;

import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.service.usuario.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    private UsuarioMapper usuarioMapper;

    public TransacaoResponse toResponse(Transacao transacao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        transacaoResponse.setId(transacao.getId());
        transacaoResponse.setDataTransacao(transacao.getDataTransacao());
        transacaoResponse.setTipoMovimentacao(transacao.getTipoMovimentacao());
        transacaoResponse.setValor(transacao.getValor());
        transacaoResponse.setUsuario(usuarioMapper.toResponse(transacao.getUsuario()));

        return transacaoResponse;
    }
}
