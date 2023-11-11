package com.jorgeolvr.servicoremessa.service;

import com.jorgeolvr.servicoremessa.api.CotacaoApi;
import com.jorgeolvr.servicoremessa.domain.Cotacao;
import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import com.jorgeolvr.servicoremessa.domain.Transacao;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.cotacao.response.ValueResponse;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.transacao.request.TransacaoRequest;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.repository.CotacaoRepository;
import com.jorgeolvr.servicoremessa.repository.TransacaoRepository;
import com.jorgeolvr.servicoremessa.repository.UsuarioRepository;
import com.jorgeolvr.servicoremessa.service.transacao.TransacaoService;
import com.jorgeolvr.servicoremessa.service.transacao.mapper.TransacaoMapper;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { TransacaoService.class })
class TransacaoServiceTest {
    @Spy
    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CotacaoApi cotacaoApi;

    @Mock
    private CotacaoRepository cotacaoRepository;

    @BeforeAll
    static void setup() {

    }

    @Test
    @DisplayName("Validação do fluxo de buscar transação pelo id")
    void buscarTransacaoPorId() {
        Long id = 1L;

        Transacao transacao = getTransacao();
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();
        Optional<Transacao> transacaoOptionalMock = Optional.of(transacao);

        when(transacaoRepository.findById(id)).thenReturn(transacaoOptionalMock);
        when(transacaoMapper.toResponse(transacaoOptionalMock.get())).thenReturn(transacaoResponseMock);
        TransacaoResponse transacaoResponse = transacaoService.buscarPorId(id);

        assertThat(transacaoResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar transação pelo tipo da movimentação")
    void buscarTransacaoPorTipoMovimentacao() {
        TipoMovimentacao tipoMovimentacao = TipoMovimentacao.SAIDA;

        Transacao transacao = getTransacao();
        List<Transacao> transacaoResponsesMock = new ArrayList<>();

        transacaoResponsesMock.add(transacao);

        when(transacaoRepository.findByTipoMovimentacao(tipoMovimentacao)).thenReturn(transacaoResponsesMock);
        List<TransacaoResponse> transacaoResponses = transacaoService.buscarPorTipoMovimentacao(tipoMovimentacao);

        assertThat(transacaoResponses).isNotEmpty();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar todas as transações")
    void buscarTodosTransacoes() {
        Transacao transacaoList[] = { getTransacao() };
        Iterable<Transacao> transacoes = Arrays.asList(transacaoList);

        when(transacaoRepository.findAll()).thenReturn(transacoes);
        List<TransacaoResponse> transacaoResponses = transacaoService.buscarTodos();

        assertThat(transacaoResponses).isNotEmpty();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar realizar transação sem cotacao cadastrada")
    void realizarTransacaoSemCotacaoCadastrada() throws Throwable {
        Transacao transacaoMock = getTransacao();
        TransacaoRequest transacaoRequestMock = getTransacaoRequest();
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        Usuario usuarioMock = getUsuario();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuarioMock);

        UsuarioResponse usuarioResponseMock = getUsuarioResponse();

        ValueResponse valueResponseMock = getValueResponse();

        when(usuarioRepository.findById(transacaoRequestMock.getOrigemId())).thenReturn(usuarioOptionalMock);
        when(usuarioMapper.toResponse(usuarioMock)).thenReturn(usuarioResponseMock);
        when(transacaoService.verificarSaldo(usuarioMapper.toResponse(usuarioMock), transacaoRequestMock.getValor())).thenReturn(true);
        when(transacaoService.montarTransacao(usuarioMock, transacaoRequestMock, TipoMovimentacao.SAIDA)).thenReturn(transacaoMock);
        when(cotacaoApi.buscarCotacaoDolarDia()).thenReturn(valueResponseMock);
        doReturn(transacaoResponseMock).when(transacaoMapper).toResponse(transacaoMock);
        TransacaoResponse transacaoResponse = transacaoService.realizarTransacao(transacaoRequestMock);

        assertThat(transacaoResponse).isNotNull();
    }

    @Test
    @DisplayName("Validação do fluxo de buscar realizar transação com cotacao cadastrada")
    void realizarTransacaoComCotacaoCadastrada() throws Throwable {
        Transacao transacaoMock = getTransacao();
        TransacaoRequest transacaoRequestMock = getTransacaoRequest();
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        Usuario usuarioMock = getUsuario();
        Optional<Usuario> usuarioOptionalMock = Optional.of(usuarioMock);

        UsuarioResponse usuarioResponseMock = getUsuarioResponse();

        Cotacao cotacaoMock = getCotacao();

        when(usuarioRepository.findById(transacaoRequestMock.getOrigemId())).thenReturn(usuarioOptionalMock);
        when(usuarioMapper.toResponse(usuarioMock)).thenReturn(usuarioResponseMock);
        when(transacaoService.verificarSaldo(usuarioMapper.toResponse(usuarioMock), transacaoRequestMock.getValor())).thenReturn(true);
        when(transacaoService.montarTransacao(usuarioMock, transacaoRequestMock, TipoMovimentacao.SAIDA)).thenReturn(transacaoMock);
        when(cotacaoRepository.findTopByOrderByIdDesc()).thenReturn(cotacaoMock);
        doReturn(transacaoResponseMock).when(transacaoMapper).toResponse(transacaoMock);
        TransacaoResponse transacaoResponse = transacaoService.realizarTransacao(transacaoRequestMock);

        assertThat(transacaoResponse).isNotNull();
    }

    Transacao getTransacao() {
        Transacao transacao = new Transacao();

        transacao.setId(1L);
        transacao.setDataTransacao(LocalDate.now());
        transacao.setValor(BigDecimal.ONE);
        transacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        transacao.setUsuario(mock(Usuario.class));

        return transacao;
    }

    TransacaoRequest getTransacaoRequest() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();

        transacaoRequest.setOrigemId(1L);
        transacaoRequest.setDestinoId(1L);
        transacaoRequest.setValor(BigDecimal.ONE);

        return transacaoRequest;
    }

    TransacaoResponse getTransacaoResponse() {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        transacaoResponse.setId(1L);
        transacaoResponse.setDataTransacao(LocalDate.now());
        transacaoResponse.setValor(BigDecimal.ONE);
        transacaoResponse.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        transacaoResponse.setUsuario(mock(UsuarioResponse.class));

        return transacaoResponse;
    }

    Usuario getUsuario() {
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

    UsuarioResponse getUsuarioResponse() {
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

    ValueResponse getValueResponse() {
        ValueResponse valueResponse = new ValueResponse();

        valueResponse.setCotacaoCompra(BigDecimal.ONE);
        valueResponse.setCotacaoVenda(BigDecimal.ONE);

        return valueResponse;
    }

    Cotacao getCotacao() {
        Cotacao cotacao = new Cotacao();

        cotacao.setDataCotacao(LocalDate.now());
        cotacao.setValor(BigDecimal.ONE);

        return cotacao;
    }
}
