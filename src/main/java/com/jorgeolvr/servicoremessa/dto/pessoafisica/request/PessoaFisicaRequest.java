package com.jorgeolvr.servicoremessa.dto.pessoafisica.request;

import com.jorgeolvr.servicoremessa.domain.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaRequest {
    private long id;

    private String cpf;

    private Usuario usuario;
}
