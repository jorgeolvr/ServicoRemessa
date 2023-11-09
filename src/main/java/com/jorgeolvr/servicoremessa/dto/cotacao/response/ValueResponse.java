package com.jorgeolvr.servicoremessa.dto.cotacao.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ValueResponse {
    private BigDecimal cotacaoCompra;

    private BigDecimal cotacaoVenda;
}
