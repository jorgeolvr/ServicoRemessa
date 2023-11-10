package com.jorgeolvr.servicoremessa.dto.pessoajuridica.request;

import com.jorgeolvr.servicoremessa.domain.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PessoaJuridicaRequest {
    private long id;

    private String cnpj;

    private Usuario usuario;
}
