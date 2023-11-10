package com.jorgeolvr.servicoremessa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class PessoaJuridica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;

    @OneToOne(mappedBy = "pessoaJuridica")
    private Usuario usuario;
}
