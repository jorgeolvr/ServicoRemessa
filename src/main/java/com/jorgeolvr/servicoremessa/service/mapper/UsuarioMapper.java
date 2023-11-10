package com.jorgeolvr.servicoremessa.service.mapper;

import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import com.jorgeolvr.servicoremessa.domain.PessoaJuridica;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.request.PessoaFisicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.request.PessoaJuridicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();

        usuario.setId(usuarioRequest.getId());
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSaldoReal(usuarioRequest.getSaldoReal());
        usuario.setSaldoDolar(usuarioRequest.getSaldoDolar());
        usuario.setTipoPessoa(usuarioRequest.getTipoPessoa());

        if (Objects.nonNull(usuarioRequest.getPessoaFisica())) {
            PessoaFisica pessoaFisica = new PessoaFisica();

            pessoaFisica.setId(usuarioRequest.getPessoaFisica().getId());
            pessoaFisica.setCpf(usuarioRequest.getPessoaFisica().getCpf());

            usuario.setPessoaFisica(pessoaFisica);
        }

        if (Objects.nonNull(usuarioRequest.getPessoaJuridica())) {
            PessoaJuridica pessoaJuridica = new PessoaJuridica();

            pessoaJuridica.setId(usuarioRequest.getPessoaJuridica().getId());
            pessoaJuridica.setCnpj(usuarioRequest.getPessoaJuridica().getCnpj());

            usuario.setPessoaJuridica(pessoaJuridica);
        }

        return usuario;
    }
    public PessoaFisica toEntity(PessoaFisicaRequest pessoaFisicaRequest) {
        PessoaFisica pessoaFisica = new PessoaFisica();

        pessoaFisica.setId(pessoaFisicaRequest.getId());
        pessoaFisica.setCpf(pessoaFisicaRequest.getCpf());

        return pessoaFisica;
    }

    public PessoaJuridica toEntity(PessoaJuridicaRequest pessoaJuridicaRequest) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setId(pessoaJuridicaRequest.getId());
        pessoaJuridica.setCnpj(pessoaJuridicaRequest.getCnpj());

        return pessoaJuridica;
    }

    public PessoaFisica toEntity(PessoaFisicaResponse pessoaFisicaResponse) {
        PessoaFisica pessoaFisica = new PessoaFisica();

        if (Objects.nonNull(pessoaFisicaResponse)) {
            pessoaFisica.setId(pessoaFisicaResponse.getId());
            pessoaFisica.setCpf(pessoaFisicaResponse.getCpf());
        }

        return pessoaFisica;
    }

    public PessoaJuridica toEntity(PessoaJuridicaResponse pessoaJuridicaResponse) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        if (Objects.nonNull(pessoaJuridicaResponse)) {
            pessoaJuridica.setId(pessoaJuridicaResponse.getId());
            pessoaJuridica.setCnpj(pessoaJuridicaResponse.getCnpj());
        }

        return pessoaJuridica;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setSenha(usuario.getSenha());
        usuarioResponse.setSaldoReal(usuario.getSaldoReal());
        usuarioResponse.setSaldoDolar(usuario.getSaldoDolar());
        usuarioResponse.setTipoPessoa(usuario.getTipoPessoa());

        if (Objects.nonNull(usuario.getPessoaFisica())) {
            PessoaFisicaResponse pessoaFisicaResponse = new PessoaFisicaResponse();

            pessoaFisicaResponse.setId(usuario.getPessoaFisica().getId());
            pessoaFisicaResponse.setCpf(usuario.getPessoaFisica().getCpf());

            usuarioResponse.setPessoaFisica(pessoaFisicaResponse);
        }

        if (Objects.nonNull(usuario.getPessoaJuridica())) {
            PessoaJuridicaResponse pessoaJuridicaResponse = new PessoaJuridicaResponse();

            pessoaJuridicaResponse.setId(usuario.getPessoaJuridica().getId());
            pessoaJuridicaResponse.setCnpj(usuario.getPessoaJuridica().getCnpj());

            usuarioResponse.setPessoaJuridica(pessoaJuridicaResponse);
        }

        return usuarioResponse;
    }
}
