package com.jorgeolvr.servicoremessa.dto.transacao.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoRequest {
    private Long id;

    private Long origemId;

    private Long destinoId;

    private BigDecimal valor;
}
