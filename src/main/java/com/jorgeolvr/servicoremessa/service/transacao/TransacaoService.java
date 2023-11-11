package com.jorgeolvr.servicoremessa.service.transacao;

import com.jorgeolvr.servicoremessa.api.CotacaoApi;
import com.jorgeolvr.servicoremessa.domain.Cotacao;
import com.jorgeolvr.servicoremessa.domain.Transacao;
import com.jorgeolvr.servicoremessa.domain.Usuario;
import com.jorgeolvr.servicoremessa.dto.transacao.request.TransacaoRequest;
import com.jorgeolvr.servicoremessa.dto.transacao.response.TransacaoResponse;
import com.jorgeolvr.servicoremessa.dto.usuario.request.UsuarioRequest;
import com.jorgeolvr.servicoremessa.dto.usuario.response.UsuarioResponse;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import com.jorgeolvr.servicoremessa.repository.CotacaoRepository;
import com.jorgeolvr.servicoremessa.repository.TransacaoRepository;
import com.jorgeolvr.servicoremessa.repository.UsuarioRepository;
import com.jorgeolvr.servicoremessa.service.transacao.mapper.TransacaoMapper;
import com.jorgeolvr.servicoremessa.service.usuario.UsuarioService;
import com.jorgeolvr.servicoremessa.service.usuario.mapper.UsuarioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TransacaoService {

    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final TransacaoRepository transacaoRepository;

    private final TransacaoMapper transacaoMapper;

    private final CotacaoApi cotacaoApi;

    private final CotacaoRepository cotacaoRepository;

    public TransacaoService(UsuarioService usuarioService, UsuarioRepository usuarioRepository,
                            UsuarioMapper usuarioMapper, TransacaoRepository transacaoRepository,
                            TransacaoMapper transacaoMapper, CotacaoApi cotacaoApi, CotacaoRepository cotacaoRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
        this.cotacaoApi = cotacaoApi;
        this.cotacaoRepository = cotacaoRepository;
    }

    @Transactional
    public TransacaoResponse buscarPorId(Long transacaoId) {
        TransacaoResponse transacaoResponse  = new TransacaoResponse();
        Optional<Transacao> transacao = transacaoRepository.findById(transacaoId);

        if (transacao.isPresent()) {
            transacaoResponse = transacaoMapper.toResponse(transacao.get());
            transacaoResponse.setUsuario(usuarioMapper.toResponse(transacao.get().getUsuario()));
        }

        return transacaoResponse;
    }

    @Transactional
    public List<TransacaoResponse> buscarPorTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        List<TransacaoResponse> transacaoResponses = new ArrayList<>();

        transacaoRepository.findByTipoMovimentacao(tipoMovimentacao).forEach(transacao -> {
            TransacaoResponse transacaoResponse = transacaoMapper.toResponse(transacao);
            transacaoResponse.setUsuario(usuarioMapper.toResponse(transacao.getUsuario()));

            transacaoResponses.add(transacaoResponse);
        });

        return transacaoResponses;
    }

    @Transactional
    public List<TransacaoResponse> buscarTodos() {
        List<TransacaoResponse> transacaoResponses = new ArrayList<>();

        transacaoRepository.findAll().forEach(transacao -> {
            TransacaoResponse transacaoResponse = transacaoMapper.toResponse(transacao);
            transacaoResponse.setUsuario(usuarioMapper.toResponse(transacao.getUsuario()));

            transacaoResponses.add(transacaoResponse);
        });

        return transacaoResponses;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=Exception.class)
    public TransacaoResponse realizarTransacao(TransacaoRequest transacaoRequest) throws Throwable {
        Usuario usuarioOrigem = buscarUsuario(transacaoRequest.getOrigemId());
        Usuario usuarioDestino = buscarUsuario(transacaoRequest.getDestinoId());

        boolean possuiSaldo = verificarSaldo(usuarioMapper.toResponse(usuarioOrigem), transacaoRequest.getValor());

        boolean possuiLimite = verificarLimiteDiario(
                usuarioMapper.toResponse(usuarioOrigem), usuarioMapper.toResponse(usuarioDestino));

        if (Objects.nonNull(usuarioOrigem) && Objects.nonNull(usuarioDestino) && possuiSaldo && possuiLimite) {
            // Adicionar movimentacao de saida na tabela
            Transacao transacaoSaida = montarTransacao(usuarioOrigem, transacaoRequest, TipoMovimentacao.SAIDA);
            transacaoRepository.save(transacaoSaida);

            // Adicionar movimentacao de entrada na tabela
            Transacao transacaoEntrada = montarTransacao(usuarioDestino, transacaoRequest, TipoMovimentacao.ENTRADA);
            transacaoRepository.save(transacaoEntrada);

            // Atualizar saldo do usuario de origem
            UsuarioRequest usuarioOrigemRequest = atualizarSaldoUsuario(usuarioOrigem, transacaoRequest, false);
            usuarioService.atualizar(usuarioOrigemRequest.getId(), usuarioOrigemRequest);

            // Atualizar saldo do usuario de destino
            UsuarioRequest usuarioDestinoRequest = atualizarSaldoUsuario(usuarioDestino, transacaoRequest, true);
            usuarioService.atualizar(usuarioDestinoRequest.getId(), usuarioDestinoRequest);

            // Retorna o dado da transacao de saída
            TransacaoResponse transacaoResponse = transacaoMapper.toResponse(transacaoSaida);
            UsuarioResponse usuarioResponse = usuarioMapper.toResponse(transacaoSaida.getUsuario());

            transacaoResponse.setUsuario(usuarioResponse);

            return transacaoResponse;
        } else {
            return null;
        }
    }

    public Usuario buscarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return usuario.orElse(null);
    }

    public boolean verificarSaldo(UsuarioResponse usuarioOrigem, BigDecimal valor) {
        boolean possuiSaldo = true;

        if (usuarioOrigem.getSaldoReal().compareTo(valor) < 0) {
            possuiSaldo = false;
        }

        return possuiSaldo;
    }

    public boolean verificarLimiteDiario(UsuarioResponse usuarioOrigem, UsuarioResponse usuarioDestino) {
        boolean possuiLimite = true;
        BigDecimal somatorioValor = BigDecimal.ZERO;

        List<Transacao> transacaoList = transacaoRepository.findByUsuarioId(usuarioOrigem.getId());

       for (Transacao transacao : transacaoList) {
           if (transacao.getDataTransacao().equals(LocalDate.now())
                   && TipoMovimentacao.SAIDA.equals(transacao.getTipoMovimentacao())) {
               somatorioValor = somatorioValor.add(transacao.getValor());
           }
       }

       if (usuarioOrigem.getTipoPessoa().equals(usuarioDestino.getTipoPessoa())) {
           BigDecimal valorLimite = buscarLimiteTipoPessoa(usuarioOrigem.getTipoPessoa(), usuarioDestino.getTipoPessoa());

           if(somatorioValor.compareTo(valorLimite) > 0) {
               possuiLimite = false;
           }
       }

        return possuiLimite;
    }

    public BigDecimal buscarLimiteTipoPessoa(TipoPessoa usuarioOrigemTipoPessoa, TipoPessoa usuarioDestinoTipoPessoa) {
        BigDecimal valorLimite = BigDecimal.ZERO;

        if (TipoPessoa.FISICA.equals(usuarioOrigemTipoPessoa) && TipoPessoa.FISICA.equals(usuarioDestinoTipoPessoa)) {
            valorLimite = BigDecimal.valueOf(10000);
        } else if (TipoPessoa.JURIDICA.equals(usuarioOrigemTipoPessoa) && TipoPessoa.JURIDICA.equals(usuarioDestinoTipoPessoa)) {
            valorLimite = BigDecimal.valueOf(50000);
        }

        return valorLimite;
    }

    public Transacao montarTransacao(Usuario usuario, TransacaoRequest transacaoRequest, TipoMovimentacao tipoMovimentacao) {
        Transacao transacao = new Transacao();

         transacao.setDataTransacao(LocalDate.now());
         transacao.setValor(transacaoRequest.getValor());
         transacao.setTipoMovimentacao(tipoMovimentacao);
         transacao.setUsuario(usuario);

         return transacao;
    }

    public UsuarioRequest atualizarSaldoUsuario(Usuario usuario, TransacaoRequest transacaoRequest,
                                                boolean isUsuarioDestino) throws Throwable {
        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setId(usuario.getId());
        usuarioRequest.setNome(usuario.getNome());
        usuarioRequest.setEmail(usuario.getEmail());
        usuarioRequest.setSenha(usuario.getSenha());

        if (!isUsuarioDestino) {
            usuarioRequest.setSaldoReal(usuario.getSaldoReal().subtract(transacaoRequest.getValor()));
            usuarioRequest.setSaldoDolar(usuario.getSaldoDolar());
        } else {
            usuarioRequest.setSaldoReal(usuario.getSaldoReal());

            BigDecimal saldoAtualizado = calcularSaldoDolar(usuario.getSaldoDolar(), transacaoRequest.getValor());
            usuarioRequest.setSaldoDolar(saldoAtualizado);
        }

        usuarioRequest.setTipoPessoa(usuario.getTipoPessoa());

        if (TipoPessoa.FISICA.equals(usuario.getTipoPessoa())) {
            usuarioRequest.setPessoaFisica(usuarioMapper.toPessoaFisicaRequest(usuario));
            usuarioRequest.setPessoaJuridica(null);
        } else {
            usuarioRequest.setPessoaFisica(null);
            usuarioRequest.setPessoaJuridica(usuarioMapper.toPessoaJuridicaRequest(usuario));
        }

        return usuarioRequest;
    }

    public BigDecimal calcularSaldoDolar(BigDecimal saldoDolar, BigDecimal valorTransacao) throws Throwable {
        BigDecimal saldoAtualizado = BigDecimal.ZERO;

        saldoAtualizado = saldoAtualizado.add(saldoDolar);

        if (Objects.isNull(cotacaoRepository.findTopByOrderByIdDesc())) {
            Cotacao cotacao = new Cotacao();

            BigDecimal cotacaoCompra = cotacaoApi.buscarCotacaoDolarDia().getCotacaoCompra();

            cotacao.setDataCotacao(LocalDate.now());
            cotacao.setValor(cotacaoCompra);

            cotacaoRepository.save(cotacao);

            saldoAtualizado = atualizarSaldoDolar(valorTransacao, cotacaoCompra, saldoAtualizado);
        } else {
            Cotacao cotacao = cotacaoRepository.findTopByOrderByIdDesc();
            saldoAtualizado = atualizarSaldoDolar(valorTransacao, cotacao.getValor(), saldoAtualizado);
        }

        return saldoAtualizado;
    }

    private static BigDecimal atualizarSaldoDolar(BigDecimal valorTransacao, BigDecimal cotacaoCompra, BigDecimal saldoAtualizado) {
        BigDecimal valorTransacaoAjustadoCotacao = valorTransacao.
                divide(cotacaoCompra, 2, RoundingMode.HALF_UP);

        saldoAtualizado = saldoAtualizado.add(valorTransacaoAjustadoCotacao);

        return saldoAtualizado;
    }
}
