package com.jorgeolvr.servicoremessa.domain;

import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(length = 50)
    private String nome;

    @NotNull
    @Column(length = 100, unique = true)
    private String email;

    @NotNull
    @Column
    private String senha;

    @NotNull
    @Column
    private BigDecimal saldoReal = BigDecimal.ZERO;

    @NotNull
    @Column
    private BigDecimal saldoDolar = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 11, name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_fisica_id", referencedColumnName = "id")
    private PessoaFisica pessoaFisica;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_juridica_id", referencedColumnName = "id")
    private PessoaJuridica pessoaJuridica;
}
