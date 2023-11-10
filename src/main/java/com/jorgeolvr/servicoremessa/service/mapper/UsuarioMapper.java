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
        PessoaFisica pessoaFisica = new PessoaFisica();
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        usuario.setId(usuarioRequest.getId());
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSaldoReal(usuarioRequest.getSaldoReal());
        usuario.setSaldoDolar(usuarioRequest.getSaldoDolar());
        usuario.setTipoPessoa(usuarioRequest.getTipoPessoa());

        if (Objects.nonNull(usuarioRequest.getPessoaFisica())) {
            pessoaFisica.setId(usuarioRequest.getPessoaFisica().getId());
            pessoaFisica.setCpf(usuarioRequest.getPessoaFisica().getCpf());
            pessoaFisica.setUsuario(usuarioRequest.getPessoaFisica().getUsuario());
        }

        if (Objects.nonNull(usuarioRequest.getPessoaJuridica())) {
            pessoaJuridica.setId(usuarioRequest.getPessoaJuridica().getId());
            pessoaJuridica.setCnpj(usuarioRequest.getPessoaJuridica().getCnpj());
            pessoaJuridica.setUsuario(usuarioRequest.getPessoaJuridica().getUsuario());
        }

        usuario.setPessoaFisica(pessoaFisica);
        usuario.setPessoaJuridica(pessoaJuridica);

        return usuario;
    }
    public PessoaFisica toEntity(PessoaFisicaRequest pessoaFisicaRequest) {
        PessoaFisica pessoaFisica = new PessoaFisica();

        pessoaFisica.setId(pessoaFisicaRequest.getId());
        pessoaFisica.setCpf(pessoaFisicaRequest.getCpf());
        pessoaFisica.setUsuario(pessoaFisicaRequest.getUsuario());

        return pessoaFisica;
    }

    public PessoaJuridica toEntity(PessoaJuridicaRequest pessoaJuridicaRequest) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setId(pessoaJuridicaRequest.getId());
        pessoaJuridica.setCnpj(pessoaJuridicaRequest.getCnpj());
        pessoaJuridica.setUsuario(pessoaJuridicaRequest.getUsuario());

        return pessoaJuridica;
    }

    public PessoaFisica toEntity(PessoaFisicaResponse pessoaFisicaResponse) {
        PessoaFisica pessoaFisica = null;

        if (Objects.nonNull(pessoaFisicaResponse)) {
            pessoaFisica.setId(pessoaFisicaResponse.getId());
            pessoaFisica.setCpf(pessoaFisicaResponse.getCpf());
            pessoaFisica.setUsuario(pessoaFisicaResponse.getUsuario());
        }

        return pessoaFisica;
    }

    public PessoaJuridica toEntity(PessoaJuridicaResponse pessoaJuridicaResponse) {
        PessoaJuridica pessoaJuridica = null;

        if (Objects.nonNull(pessoaJuridicaResponse)) {
            pessoaJuridica.setId(pessoaJuridicaResponse.getId());
            pessoaJuridica.setCnpj(pessoaJuridicaResponse.getCnpj());
            pessoaJuridica.setUsuario(pessoaJuridicaResponse.getUsuario());
        }

        return pessoaJuridica;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        PessoaFisicaResponse pessoaFisicaResponse = new PessoaFisicaResponse();
        PessoaJuridicaResponse pessoaJuridicaResponse = new PessoaJuridicaResponse();

        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNome(usuario.getNome());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setSenha(usuario.getSenha());
        usuarioResponse.setSaldoReal(usuario.getSaldoReal());
        usuarioResponse.setSaldoDolar(usuario.getSaldoDolar());
        usuarioResponse.setTipoPessoa(usuario.getTipoPessoa());

        if (Objects.nonNull(usuario.getPessoaFisica())) {
            pessoaFisicaResponse.setId(usuario.getPessoaFisica().getId());
            pessoaFisicaResponse.setCpf(usuario.getPessoaFisica().getCpf());
            pessoaFisicaResponse.setUsuario(usuario.getPessoaFisica().getUsuario());
        }

        if (Objects.nonNull(usuario.getPessoaJuridica())) {
            pessoaJuridicaResponse.setId(usuario.getPessoaJuridica().getId());
            pessoaJuridicaResponse.setCnpj(usuario.getPessoaJuridica().getCnpj());
            pessoaJuridicaResponse.setUsuario(usuario.getPessoaJuridica().getUsuario());
        }

        usuarioResponse.setPessoaFisica(pessoaFisicaResponse);
        usuarioResponse.setPessoaJuridica(pessoaJuridicaResponse);

        return usuarioResponse;
    }
}
