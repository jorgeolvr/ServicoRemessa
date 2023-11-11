package com.jorgeolvr.servicoremessa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Cotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name="valor", columnDefinition="Decimal(10,2) default '0.0000'")
    private BigDecimal valor = BigDecimal.ZERO;

    @NotNull
    @Column
    private LocalDate dataCotacao;
}
