package com.jorgeolvr.servicoremessa.service.transacao.mapper;

import com.jorgeolvr.servicoremessa.domain.Transacao;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransacaoMapper {
    public TransacaoResponse toResponse(Transacao transacao) {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        transacaoResponse.setId(transacao.getId());
        transacaoResponse.setDataTransacao(transacao.getDataTransacao());
        transacaoResponse.setTipoMovimentacao(transacao.getTipoMovimentacao());
        transacaoResponse.setValor(transacao.getValor());
        transacaoResponse.setUsuario(getUsuario(transacao));

        return transacaoResponse;
    }

    public UsuarioResponse getUsuario(Transacao transacao) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setId(transacao.getUsuario().getId());
        usuarioResponse.setNome(transacao.getUsuario().getNome());
        usuarioResponse.setEmail(transacao.getUsuario().getEmail());
        usuarioResponse.setSenha(transacao.getUsuario().getSenha());
        usuarioResponse.setSaldoReal(transacao.getUsuario().getSaldoReal());
        usuarioResponse.setSaldoDolar(transacao.getUsuario().getSaldoDolar());
        usuarioResponse.setTipoPessoa(transacao.getUsuario().getTipoPessoa());

        if (Objects.nonNull(transacao.getUsuario().getPessoaFisica())) {
            PessoaFisicaResponse pessoaFisicaResponse = new PessoaFisicaResponse();

            pessoaFisicaResponse.setId(transacao.getUsuario().getPessoaFisica().getId());
            pessoaFisicaResponse.setCpf(transacao.getUsuario().getPessoaFisica().getCpf());

            usuarioResponse.setPessoaFisica(pessoaFisicaResponse);
        }

        if (Objects.nonNull(transacao.getUsuario().getPessoaJuridica())) {
            PessoaJuridicaResponse pessoaJuridicaResponse = new PessoaJuridicaResponse();

            pessoaJuridicaResponse.setId(transacao.getUsuario().getPessoaJuridica().getId());
            pessoaJuridicaResponse.setCnpj(transacao.getUsuario().getPessoaJuridica().getCnpj());

            usuarioResponse.setPessoaJuridica(pessoaJuridicaResponse);
        }

        return usuarioResponse;
    }
}
