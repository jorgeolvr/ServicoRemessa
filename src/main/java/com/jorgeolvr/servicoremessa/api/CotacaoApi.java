package com.jorgeolvr.servicoremessa.api;

import com.jorgeolvr.servicoremessa.dto.cotacao.response.CotacaoResponse;
import com.jorgeolvr.servicoremessa.dto.cotacao.response.ValueResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.function.BiFunction;

@Slf4j
@Component
public class CotacaoApi {
    private static final String BASE_URL = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata";

    private final WebClient webClient;

    public CotacaoApi(WebClient.Builder builder) {
        webClient = builder.baseUrl(BASE_URL).build();
    }

    public ValueResponse buscarCotacaoDolarDia() throws Throwable {
        ValueResponse valueResponse = new ValueResponse();
        LocalDate diaAtual = LocalDate.now();

        try {
            CotacaoResponse cotacaoResponse = getCotacao(diaAtual);

            /** Caso o valor retornado pela api de cotação esteja vazio por ser final de
             *  semana ou feriado então deve-se buscar a última cotação válida
             */
            if (cotacaoResponse.getValue().isEmpty()) {
                valueResponse = buscarUltimaCotacaoValida(diaAtual);
            } else {
                valueResponse = cotacaoResponse.getValue().get(0);
            }
        } catch (Exception e) {
            throw Exceptions.unwrap(e);
        }

        return valueResponse;
    }

    private CotacaoResponse getCotacao(LocalDate localDate) {
        CotacaoResponse cotacaoResponse = webClient.get().uri(builder -> builder.path("/CotacaoDolarDia(dataCotacao=@dataCotacao)")
                        .queryParam("@dataCotacao", "'" + localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "'")
                        .queryParam("$top", "100")
                        .queryParam("$skip", "0")
                        .queryParam("$format", "json").build())
                .accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(CotacaoResponse.class).block();
        return cotacaoResponse;
    }

    public ValueResponse buscarUltimaCotacaoValida(LocalDate data) {
        ValueResponse valueResponse = new ValueResponse();

        // Buscar cotação de datas anteriores até achar um valor válido
        while (Objects.isNull(valueResponse.getCotacaoCompra())) {
            data = data.minusDays(1);

            if (!getCotacao(data).getValue().isEmpty()) {
                valueResponse = getCotacao(data).getValue().get(0);
            }
        }

        return valueResponse;
    }
}
