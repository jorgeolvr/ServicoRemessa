package com.jorgeolvr.servicoremessa.web.rest.v1.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgeolvr.servicoremessa.ServicoRemessaApplication;
import com.jorgeolvr.servicoremessa.dto.transacao.request.TransacaoRequest;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import com.jorgeolvr.servicoremessa.service.transacao.TransacaoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ServicoRemessaApplication.class)
@AutoConfigureMockMvc
@WebMvcTest(value = TransacaoController.class)
class TransacaoControllerTest {

    private final String PATH = "/api/v1/backend/transacao";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransacaoService transacaoService;

    @BeforeAll
    static void setup() {

    }

    @Test
    @DisplayName("Validação do endpoint de buscar transação pelo id com status ok")
    void buscarTransacaoPorIdOk() throws Exception {
        Long id = 1L;
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        when(transacaoService.buscarPorId(id)).thenReturn(transacaoResponseMock);
        mvc.perform(get(PATH + "/buscar-por-id/" + id)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar transação pelo id com status no content")
    void buscarTransacaoPorIdNoContent() throws Exception {
        Long id = 1L;
        TransacaoResponse transacaoResponseMock = null;

        when(transacaoService.buscarPorId(id)).thenReturn(transacaoResponseMock);
        mvc.perform(get(PATH + "/buscar-por-id/" + id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar transação pelo tipo de movimentacao com status ok")
    void buscarTransacaoPorTipoMovimentacaoOk() throws Exception {
        TipoMovimentacao tipoMovimentacao = TipoMovimentacao.SAIDA;

        List<TransacaoResponse> transacaoResponses = new ArrayList<>();
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        transacaoResponses.add(transacaoResponseMock);

        when(transacaoService.buscarPorTipoMovimentacao(tipoMovimentacao)).thenReturn(transacaoResponses);
        mvc.perform(get(PATH + "/buscar-por-tipo-movimentacao/" + tipoMovimentacao)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar transação pelo tipo de movimentacao com status no content")
    void buscarTransacaoPorTipoMovimentacaoNoContent() throws Exception {
        TipoMovimentacao tipoMovimentacao = TipoMovimentacao.SAIDA;

        List<TransacaoResponse> transacaoResponses = new ArrayList<>();

        when(transacaoService.buscarPorTipoMovimentacao(tipoMovimentacao)).thenReturn(transacaoResponses);
        mvc.perform(get(PATH + "/buscar-por-tipo-movimentacao/" + tipoMovimentacao)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar todas as transações com status ok")
    void buscarTodosTransacoesOk() throws Exception {
        List<TransacaoResponse> transacaoResponses = new ArrayList<>();

        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        transacaoResponses.add(transacaoResponseMock);

        when(transacaoService.buscarTodos()).thenReturn(transacaoResponses);
        mvc.perform(get(PATH)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar todas as transações com status no content")
    void buscarTodosTransacoesNoContent() throws Exception {
        List<TransacaoResponse> transacaoResponses = new ArrayList<>();

        when(transacaoService.buscarTodos()).thenReturn(transacaoResponses);
        mvc.perform(get(PATH)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de realizar uma transacao com status ok")
    void realizarTransacaoOk() throws Throwable {
        TransacaoRequest transacaoRequestMock = getTransacaoRequest();
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        when(transacaoService.realizarTransacao(transacaoRequestMock)).thenReturn(transacaoResponseMock);
        mvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transacaoRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de realizar uma transacao com status bad request")
    void realizarTransacaoBadRequest() throws Throwable {
        TransacaoRequest transacaoRequestMock = null;
        TransacaoResponse transacaoResponseMock = getTransacaoResponse();

        when(transacaoService.realizarTransacao(transacaoRequestMock)).thenReturn(transacaoResponseMock);
        mvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(transacaoRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
    }

    TransacaoRequest getTransacaoRequest() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();

        transacaoRequest.setValor(BigDecimal.ONE);
        transacaoRequest.setOrigemId(1L);
        transacaoRequest.setDestinoId(2L);

        return transacaoRequest;
    }

    TransacaoResponse getTransacaoResponse() {
        TransacaoResponse transacaoResponse = new TransacaoResponse();

        transacaoResponse.setValor(BigDecimal.ONE);
        transacaoResponse.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        transacaoResponse.setDataTransacao(LocalDate.now());
        transacaoResponse.setUsuario(mock(UsuarioResponse.class));

        return transacaoResponse;
    }
}
