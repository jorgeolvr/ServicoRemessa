package com.jorgeolvr.servicoremessa.web.rest.v1.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgeolvr.servicoremessa.ServicoRemessaApplication;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.request.PessoaFisicaRequest;
import com.jorgeolvr.servicoremessa.dto.pessoafisica.response.PessoaFisicaResponse;
import com.jorgeolvr.servicoremessa.dto.pessoajuridica.response.PessoaJuridicaResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.service.UsuarioService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = ServicoRemessaApplication.class)
@AutoConfigureMockMvc
@WebMvcTest(value = UsuarioController.class)
class UsuarioControllerTest {

    private final String PATH = "/api/v1/backend/usuario";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService usuarioService;

    @BeforeAll
    static void setup() {

    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo id com status ok")
    void buscarUsuarioPorIdOk() throws Exception {
        Long id = 1L;
        UsuarioResponse usuarioResponseMock = getUsuarioPesoaFisicaResponse();

        when(usuarioService.buscarPorId(id)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/id/" + id)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo id com status no content")
    void buscarUsuarioPorIdNoContent() throws Exception {
        Long id = 1L;
        UsuarioResponse usuarioResponseMock = null;

        when(usuarioService.buscarPorId(id)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/id/" + id)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo cpf com status ok")
    void buscarUsuarioPorCpfOk() throws Exception {
        String cpf = "33833961007";
        UsuarioResponse usuarioResponseMock = getUsuarioPesoaFisicaResponse();

        when(usuarioService.buscarPorCpf(cpf)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/cpf/" + cpf)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo cpf com status no content")
    void buscarUsuarioPorCpfNoContent() throws Exception {
        String cpf = "33833961007";
        UsuarioResponse usuarioResponseMock = new UsuarioResponse();

        when(usuarioService.buscarPorCpf(cpf)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/cpf/" + cpf)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo cnpj com status ok")
    void buscarUsuarioPorCnpjOk() throws Exception {
        String cnpj = "33833961007";
        UsuarioResponse usuarioResponseMock = getUsuarioPesoaJuridicaResponse();

        when(usuarioService.buscarPorCnpj(cnpj)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/cnpj/" + cnpj)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar usuário pelo cnpj com status no content")
    void buscarUsuarioPorCnpjNoContent() throws Exception {
        String cnpj = "72872933000180";
        UsuarioResponse usuarioResponseMock = new UsuarioResponse();

        when(usuarioService.buscarPorCnpj(cnpj)).thenReturn(usuarioResponseMock);
        mvc.perform(get(PATH + "/cnpj/" + cnpj)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar todos os usuários com status ok")
    void buscarTodosUsuariosOk() throws Exception {
        List<UsuarioResponse> usuarioResponses = new ArrayList<>();

        UsuarioResponse usuarioResponseMock = getUsuarioPesoaFisicaResponse();

        usuarioResponses.add(usuarioResponseMock);

        when(usuarioService.buscarTodos()).thenReturn(usuarioResponses);
        mvc.perform(get(PATH)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de buscar todos os usuários com status no content")
    void buscarTodosUsuariosNoContent() throws Exception {
        List<UsuarioResponse> usuarioResponses = new ArrayList<>();

        when(usuarioService.buscarTodos()).thenReturn(usuarioResponses);
        mvc.perform(get(PATH)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Validação do endpoint de cadastrar um usuário com status created")
    void criarUsuarioCreated() throws Exception {
        UsuarioRequest usuarioRequestMock = getUsuarioRequest();
        UsuarioResponse usuarioResponseMock = getUsuarioPesoaFisicaResponse();

        when(usuarioService.criar(usuarioRequestMock)).thenReturn(usuarioResponseMock);
        mvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(usuarioRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Validação do endpoint de cadastrar um usuário com status server error")
    void criarUsuarioServerError() throws Exception {
        UsuarioRequest usuarioRequestMock = getUsuarioRequest();
        UsuarioResponse usuarioResponseMock = null;

        when(usuarioService.criar(usuarioRequestMock)).thenReturn(usuarioResponseMock);
        mvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(usuarioRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Validação do endpoint de atualizar um usuário com status ok")
    void atualizarUsuarioOk() throws Exception {
        Long id = 1L;
        UsuarioRequest usuarioRequestMock = getUsuarioRequest();
        UsuarioResponse usuarioResponseMock = getUsuarioPesoaFisicaResponse();

        when(usuarioService.atualizar(id, usuarioRequestMock)).thenReturn(usuarioResponseMock);
        mvc.perform(put(PATH + "/" + id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(usuarioRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Validação do endpoint de atualizar um usuário com status not found")
    void atualizarUsuarioNotFound() throws Exception {
        Long id = 1L;
        UsuarioRequest usuarioRequestMock = getUsuarioRequest();
        UsuarioResponse usuarioResponseMock = null;

        when(usuarioService.atualizar(id, usuarioRequestMock)).thenReturn(usuarioResponseMock);
        mvc.perform(put(PATH + "/" + id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(usuarioRequestMock))
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
    }

    UsuarioRequest getUsuarioRequest() {
        PessoaFisicaRequest pessoaFisicaRequest = new PessoaFisicaRequest();
        pessoaFisicaRequest.setCpf("33833961007");

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome("Usuario");
        usuarioRequest.setEmail("usuario@usuario.com");
        usuarioRequest.setSenha("123");
        usuarioRequest.setSaldoReal(BigDecimal.ZERO);
        usuarioRequest.setSaldoDolar(BigDecimal.ZERO);
        usuarioRequest.setTipoPessoa(TipoPessoa.FISICA);
        usuarioRequest.setPessoaFisica(pessoaFisicaRequest);
        usuarioRequest.setPessoaJuridica(null);

        return usuarioRequest;
    }

    UsuarioResponse getUsuarioPesoaFisicaResponse() {
        PessoaFisicaResponse pessoaFisicaResponse = new PessoaFisicaResponse();
        pessoaFisicaResponse.setCpf("72872933000180");

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setNome("Usuario");
        usuarioResponse.setEmail("usuario@usuario.com");
        usuarioResponse.setSenha("123");
        usuarioResponse.setSaldoReal(BigDecimal.ZERO);
        usuarioResponse.setSaldoDolar(BigDecimal.ZERO);
        usuarioResponse.setTipoPessoa(TipoPessoa.FISICA);
        usuarioResponse.setPessoaFisica(pessoaFisicaResponse);
        usuarioResponse.setPessoaJuridica(null);

        return usuarioResponse;
    }

    UsuarioResponse getUsuarioPesoaJuridicaResponse() {
        PessoaJuridicaResponse pessoaJuridicaResponse = new PessoaJuridicaResponse();
        pessoaJuridicaResponse.setCnpj("33833961007");

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setNome("Usuario");
        usuarioResponse.setEmail("usuario@usuario.com");
        usuarioResponse.setSenha("123");
        usuarioResponse.setSaldoReal(BigDecimal.ZERO);
        usuarioResponse.setSaldoDolar(BigDecimal.ZERO);
        usuarioResponse.setTipoPessoa(TipoPessoa.FISICA);
        usuarioResponse.setPessoaFisica(null);
        usuarioResponse.setPessoaJuridica(pessoaJuridicaResponse);

        return usuarioResponse;
    }
}
