package com.jorgeolvr.servicoremessa.dto.usuario.request;

import com.jorgeolvr.servicoremessa.dto.pessoafisica.request.PessoaFisicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UsuarioRequest {
    private long id;

    private String nome;

    private String email;

    private String senha;

    private BigDecimal saldoReal;

    private BigDecimal saldoDolar;

    private TipoPessoa tipoPessoa;

    private PessoaFisicaRequest pessoaFisica;

    private PessoaJuridicaResponse pessoaJuridica;


}
