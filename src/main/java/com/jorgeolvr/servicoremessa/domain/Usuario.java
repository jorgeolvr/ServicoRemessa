package com.jorgeolvr.servicoremessa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorgeolvr.servicoremessa.enums.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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

    @Column(name="saldoReal", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal saldoReal = BigDecimal.ZERO;

    @Column(name="saldoDolar", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal saldoDolar = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 11, name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_fisica_id", referencedColumnName = "id")
    private PessoaFisica pessoaFisica;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_juridica_id", referencedColumnName = "id")
    private PessoaJuridica pessoaJuridica;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Transacao> transacao;
}
