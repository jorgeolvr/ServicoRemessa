package com.jorgeolvr.servicoremessa.service;

import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import com.jorgeolvr.servicoremessa.domain.PessoaJuridica;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.repository.PessoaFisicaRepository;
import com.jorgeolvr.servicoremessa.repository.PessoaJuridicaRepository;
import com.jorgeolvr.servicoremessa.repository.UsuarioRepository;
import com.jorgeolvr.servicoremessa.service.mapper.UsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PessoaFisicaRepository pessoaFisicaRepository,
                          PessoaJuridicaRepository pessoaJuridicaRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest usuarioRequest) {
        PessoaFisica _pessoaFisica = null;
        PessoaJuridica _pessoaJuridica = null;

        if (TipoPessoa.FISICA.equals(usuarioRequest.getTipoPessoa())) {
            _pessoaFisica = pessoaFisicaRepository.save(usuarioMapper.toEntity(usuarioRequest.getPessoaFisica()));
        } else {
            _pessoaJuridica = pessoaJuridicaRepository.save(usuarioMapper.toEntity(usuarioRequest.getPessoaJuridica()));
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(usuarioRequest.getSenha());
        usuario.setSaldoReal(usuarioRequest.getSaldoReal());
        usuario.setSaldoDolar(usuarioRequest.getSaldoDolar());
        usuario.setTipoPessoa(usuarioRequest.getTipoPessoa());
        usuario.setPessoaFisica(_pessoaFisica);
        usuario.setPessoaJuridica(_pessoaJuridica);

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }
}
