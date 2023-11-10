package com.jorgeolvr.servicoremessa.dto.usuario.response;

import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UsuarioResponse {
    private long id;

    private String nome;

    private String email;

    private String senha;

    private BigDecimal saldoReal;

    private BigDecimal saldoDolar;

    private TipoPessoa tipoPessoa;

    private PessoaFisicaResponse pessoaFisica;

    private PessoaJuridicaResponse pessoaJuridica;
}
