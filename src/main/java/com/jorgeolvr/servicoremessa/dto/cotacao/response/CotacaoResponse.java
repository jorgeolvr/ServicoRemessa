package com.jorgeolvr.servicoremessa.dto.cotacao.response;

import lombok.Data;

import java.util.List;

@Data
public class CotacaoResponse {
    private List<ValueResponse> value;
}
