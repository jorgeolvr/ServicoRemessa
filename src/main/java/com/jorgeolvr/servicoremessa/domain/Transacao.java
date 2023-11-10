package com.jorgeolvr.servicoremessa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
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
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name="valor", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal valor = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 11, name = "tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;

    @NotNull
    @Column
    private LocalDate dataTransacao;

    @JsonIgnore
    @JoinColumn(name = "usuario_id", nullable = false)
    @ManyToOne
    private Usuario usuario;
}
