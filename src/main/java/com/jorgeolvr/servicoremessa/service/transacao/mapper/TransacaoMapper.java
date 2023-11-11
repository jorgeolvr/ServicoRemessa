package com.jorgeolvr.servicoremessa.service.transacao.mapper;

import com.jorgeolvr.servicoremessa.domain.Transacao;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {
    public TransacaoResponse toResponse(Transacao transacao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        transacaoResponse.setId(transacao.getId());
        transacaoResponse.setDataTransacao(transacao.getDataTransacao());
        transacaoResponse.setTipoMovimentacao(transacao.getTipoMovimentacao());
        transacaoResponse.setValor(transacao.getValor());

        return transacaoResponse;
    }
}
