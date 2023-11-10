package com.jorgeolvr.servicoremessa.service.usuario;

import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import com.jorgeolvr.servicoremessa.domain.PessoaJuridica;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.repository.PessoaFisicaRepository;
import com.jorgeolvr.servicoremessa.repository.PessoaJuridicaRepository;
import com.jorgeolvr.servicoremessa.repository.UsuarioRepository;
import com.jorgeolvr.servicoremessa.service.usuario.mapper.UsuarioMapper;
import com.jorgeolvr.servicoremessa.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UsuarioResponse buscarPorId(Long usuarioId) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isPresent()) {
            usuarioResponse = usuarioMapper.toResponse(usuario.get());
        }

        return usuarioResponse;
    }

    @Transactional
    public UsuarioResponse buscarPorCpf(String cpf) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);

        if (usuario.isPresent()) {
            usuarioResponse = usuarioMapper.toResponse(usuario.get());
        }

        return usuarioResponse;
    }

    @Transactional
    public UsuarioResponse buscarPorCnpj(String cnpj) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        Optional<Usuario> usuario = usuarioRepository.findByCnpj(cnpj);

        if (usuario.isPresent()) {
            usuarioResponse = usuarioMapper.toResponse(usuario.get());
        }

        return usuarioResponse;
    }

    @Transactional
    public List<UsuarioResponse> buscarTodos() {
        List<UsuarioResponse> usuarioResponses = new ArrayList<>();

        usuarioRepository.findAll().forEach(usuario -> {
            UsuarioResponse usuarioResponse = usuarioMapper.toResponse(usuario);
            usuarioResponses.add(usuarioResponse);
        });

        return usuarioResponses;
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
        montarUsuario(usuario, usuarioRequest, _pessoaFisica, _pessoaJuridica);

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest usuarioRequest) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            PessoaFisica _pessoaFisica = null;
            PessoaJuridica _pessoaJuridica = null;

            if (TipoPessoa.FISICA.equals(usuarioRequest.getTipoPessoa())) {
                Optional<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findById(usuario.get().getPessoaFisica().getId());

                if (pessoaFisica.isPresent()) {
                    _pessoaFisica = pessoaFisica.get();
                    _pessoaFisica.setCpf(usuarioRequest.getPessoaFisica().getCpf());
                }
            } else {
                Optional<PessoaJuridica> pessoaJuridica = pessoaJuridicaRepository.findById(usuario.get().getPessoaJuridica().getId());

                if (pessoaJuridica.isPresent()) {
                    _pessoaJuridica = pessoaJuridica.get();
                    _pessoaJuridica.setCnpj(usuarioRequest.getPessoaJuridica().getCnpj());
                }
            }


            Usuario _usuario = usuario.get();
            montarUsuario(_usuario, usuarioRequest, _pessoaFisica, _pessoaJuridica);

            return usuarioMapper.toResponse(usuarioRepository.save(_usuario));
        } else {
            return null;
        }
    }

    public void montarUsuario(Usuario usuario, UsuarioRequest usuarioRequest, PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica) {
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(PasswordUtils.criptografarSenha(usuarioRequest.getSenha()));
        usuario.setSaldoReal(usuarioRequest.getSaldoReal());
        usuario.setSaldoDolar(usuarioRequest.getSaldoDolar());
        usuario.setTipoPessoa(usuarioRequest.getTipoPessoa());
        usuario.setPessoaFisica(pessoaFisica);
        usuario.setPessoaJuridica(pessoaJuridica);
    }
}
