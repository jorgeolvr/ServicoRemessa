package com.jorgeolvr.servicoremessa.dto.pessoafisica.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaResponse {
    private long id;

    private String cpf;
}
