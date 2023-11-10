package com.jorgeolvr.servicoremessa.dto.transacao.response;

import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransacaoResponse {
    private long id;

    private BigDecimal valor;

    private TipoMovimentacao tipoMovimentacao;

    private LocalDate dataTransacao;

    private Usuario usuario;
}
