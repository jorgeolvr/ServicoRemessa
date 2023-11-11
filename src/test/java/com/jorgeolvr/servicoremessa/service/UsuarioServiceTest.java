package com.jorgeolvr.servicoremessa.service;

import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import com.jorgeolvr.servicoremessa.domain.PessoaJuridica;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.request.PessoaFisicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.request.PessoaJuridicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.repository.PessoaFisicaRepository;
import com.jorgeolvr.servicoremessa.repository.PessoaJuridicaRepository;
import com.jorgeolvr.servicoremessa.repository.UsuarioRepository;
import com.jorgeolvr.servicoremessa.service.usuario.UsuarioService;
import com.jorgeolvr.servicoremessa.service.usuario.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { UsuarioService.class })
class UsuarioServiceTest {

    @Spy
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @BeforeAll
    static void setup() {

    }

    @Test
    @DisplayName("Validação do fluxo de buscar usuário pelo id")
    void buscarUsuarioPorId() {
        Long id = 1L;

        Usuario usuario = getUsuarioPessoaFisica();
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaFisicaResponse();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuario);

        when(usuarioRepository.findById(id)).thenReturn(usuarioOptionalMock);
        when(usuarioMapper.toResponse(usuarioOptionalMock.get())).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.buscarPorId(id);

        assertThat(usuarioResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar usuário pessoa física pelo cpf")
    void buscarUsuarioPessoaFisicaPorCpf() {
        String cpf = "33833961007";

        Usuario usuario = getUsuarioPessoaFisica();
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaFisicaResponse();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuario);

        when(usuarioRepository.findByCpf(cpf)).thenReturn(usuarioOptionalMock);
        when(usuarioMapper.toResponse(usuarioOptionalMock.get())).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.buscarPorCpf(cpf);

        assertThat(usuarioResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar usuário pessoa jurídica pelo cnpj")
    void buscarUsuarioPessoaFisicaPorCnpj() {
        String cnpj = "72872933000180";

        Usuario usuario = getUsuarioPessoaJuridica();
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaJuridicaResponse();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuario);

        when(usuarioRepository.findByCnpj(cnpj)).thenReturn(usuarioOptionalMock);
        when(usuarioMapper.toResponse(usuarioOptionalMock.get())).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.buscarPorCnpj(cnpj);

        assertThat(usuarioResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar todos os usuários")
    void buscarTodosUsuarios() {
        Usuario usuarioList[] = { getUsuarioPessoaFisica() };
        Iterable<Usuario> usuarios = Arrays.asList(usuarioList);

        when(usuarioRepository.findAll()).thenReturn(usuarios);
        List<UsuarioResponse> usuarioResponses = usuarioService.buscarTodos();

        assertThat(usuarioResponses).isNotEmpty();
    }

    @Test
    @DisplayName("Validação do fluxo de criação de um novo usuário pessoa física")
    void criarUsuarioPessoaFisica() {
        Usuario usuarioMock = getUsuarioPessoaFisica();
        PessoaFisica pessoaFisicaMock = getPessoaFisica();

        UsuarioRequest usuarioRequestMock = getUsuarioPessoaFisicaRequest();
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaFisicaResponse();

        when(pessoaFisicaRepository.save(usuarioMapper.toEntity(usuarioRequestMock.getPessoaFisica()))).thenReturn(pessoaFisicaMock);
        when(usuarioMapper.toResponse(usuarioRepository.save(usuarioMock))).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.criar(usuarioRequestMock);

        assertThat(usuarioResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de criação de um novo usuário pessoa jurídica")
    void criarUsuarioPessoaJuridica() {
        Usuario usuarioMock = getUsuarioPessoaJuridica();
        PessoaJuridica pessoaJuridicaMock = getPessoaJuridica();

        UsuarioRequest usuarioRequestMock = getUsuarioPessoaJuridicaRequest();
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaJuridicaResponse();

        when(pessoaJuridicaRepository.save(usuarioMapper.toEntity(usuarioRequestMock.getPessoaJuridica()))).thenReturn(pessoaJuridicaMock);
        when(usuarioMapper.toResponse(usuarioRepository.save(usuarioMock))).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.criar(usuarioRequestMock);

        assertThat(usuarioResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de atualizar um usuário pessoa física")
    void atualizarUsuarioPessoaFisica() {
        Long id = 1L;

        Usuario usuarioMock = getUsuarioPessoaFisica();
        UsuarioRequest usuarioRequestMock = getUsuarioPessoaFisicaRequest();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuarioMock);
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaFisicaResponse();

        PessoaFisica pessoaFisicaMock = getPessoaFisica();
        Optional<PessoaFisica> pessoaFisicaOptionalMock = Optional.of(pessoaFisicaMock);

        when(usuarioRepository.findById(id)).thenReturn(usuarioOptionalMock);
        when(pessoaFisicaRepository.findById(usuarioRequestMock.getPessoaFisica().getId())).thenReturn(pessoaFisicaOptionalMock);
        when(usuarioMapper.toResponse(usuarioRepository.save(usuarioMock))).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.atualizar(id, usuarioRequestMock);

        assertThat(usuarioResponse.getId()).isEqualTo(usuarioRequestMock.getId());
    }

    @Test
    @DisplayName("Validação do fluxo de atualizar um usuário pessoa jurídica")
    void atualizarUsuarioPessoaJuridica() {
        Long id = 1L;

        Usuario usuarioMock = getUsuarioPessoaJuridica();
        UsuarioRequest usuarioRequestMock = getUsuarioPessoaJuridicaRequest();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuarioMock);
        UsuarioResponse usuarioResponseMock = getUsuarioPessoaJuridicaResponse();

        PessoaJuridica pessoaJuridicaMock = getPessoaJuridica();
        Optional<PessoaJuridica> pessoaJuridicaOptionalMock = Optional.of(pessoaJuridicaMock);

        when(usuarioRepository.findById(id)).thenReturn(usuarioOptionalMock);
        when(pessoaJuridicaRepository.findById(usuarioRequestMock.getPessoaJuridica().getId())).thenReturn(pessoaJuridicaOptionalMock);
        when(usuarioMapper.toResponse(usuarioRepository.save(usuarioMock))).thenReturn(usuarioResponseMock);
        UsuarioResponse usuarioResponse = usuarioService.atualizar(id, usuarioRequestMock);

        assertThat(usuarioResponse.getId()).isEqualTo(usuarioRequestMock.getId());
    }

    Usuario getUsuarioPessoaFisica() {
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNome("Usuario");
        usuario.setEmail("usuario@usuario.com");
        usuario.setSenha("123");
        usuario.setSaldoReal(BigDecimal.ZERO);
        usuario.setSaldoDolar(BigDecimal.ZERO);
        usuario.setTipoPessoa(TipoPessoa.FISICA);
        usuario.setPessoaFisica(mock(PessoaFisica.class));
        usuario.setPessoaJuridica(null);

        return usuario;
    }

    Usuario getUsuarioPessoaJuridica() {
        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNome("Usuario");
        usuario.setEmail("usuario@usuario.com");
        usuario.setSenha("123");
        usuario.setSaldoReal(BigDecimal.ZERO);
        usuario.setSaldoDolar(BigDecimal.ZERO);
        usuario.setTipoPessoa(TipoPessoa.JURIDICA);
        usuario.setPessoaFisica(null);
        usuario.setPessoaJuridica(mock(PessoaJuridica.class));

        return usuario;
    }

    PessoaFisica getPessoaFisica() {
        PessoaFisica pessoaFisica = new PessoaFisica();

        pessoaFisica.setId(1L);
        pessoaFisica.setCpf("33833961007");

        return pessoaFisica;
    }

    PessoaJuridica getPessoaJuridica() {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setId(1L);
        pessoaJuridica.setCnpj("72872933000180");

        return pessoaJuridica;
    }


    UsuarioRequest getUsuarioPessoaFisicaRequest() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setNome("Usuario");
        usuarioRequest.setEmail("usuario@usuario.com");
        usuarioRequest.setSenha("123");
        usuarioRequest.setSaldoReal(BigDecimal.ZERO);
        usuarioRequest.setSaldoDolar(BigDecimal.ZERO);
        usuarioRequest.setTipoPessoa(TipoPessoa.FISICA);
        usuarioRequest.setPessoaFisica(mock(PessoaFisicaRequest.class));
        usuarioRequest.setPessoaJuridica(null);

        return usuarioRequest;
    }

    UsuarioRequest getUsuarioPessoaJuridicaRequest() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setNome("Usuario");
        usuarioRequest.setEmail("usuario@usuario.com");
        usuarioRequest.setSenha("123");
        usuarioRequest.setSaldoReal(BigDecimal.ZERO);
        usuarioRequest.setSaldoDolar(BigDecimal.ZERO);
        usuarioRequest.setTipoPessoa(TipoPessoa.JURIDICA);
        usuarioRequest.setPessoaFisica(null);
        usuarioRequest.setPessoaJuridica(mock(PessoaJuridicaRequest.class));

        return usuarioRequest;
    }

    UsuarioResponse getUsuarioPessoaFisicaResponse() {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setNome("Usuario");
        usuarioResponse.setEmail("usuario@usuario.com");
        usuarioResponse.setSenha("123");
        usuarioResponse.setSaldoReal(BigDecimal.ZERO);
        usuarioResponse.setSaldoDolar(BigDecimal.ZERO);
        usuarioResponse.setTipoPessoa(TipoPessoa.FISICA);
        usuarioResponse.setPessoaFisica(mock(PessoaFisicaResponse.class));
        usuarioResponse.setPessoaJuridica(null);

        return usuarioResponse;
    }

    UsuarioResponse getUsuarioPessoaJuridicaResponse() {
        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setNome("Usuario");
        usuarioResponse.setEmail("usuario@usuario.com");
        usuarioResponse.setSenha("123");
        usuarioResponse.setSaldoReal(BigDecimal.ZERO);
        usuarioResponse.setSaldoDolar(BigDecimal.ZERO);
        usuarioResponse.setTipoPessoa(TipoPessoa.JURIDICA);
        usuarioResponse.setPessoaFisica(null);
        usuarioResponse.setPessoaJuridica(mock(PessoaJuridicaResponse.class));

        return usuarioResponse;
    }

}
