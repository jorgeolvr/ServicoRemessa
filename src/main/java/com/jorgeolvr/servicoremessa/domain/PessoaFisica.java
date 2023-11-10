package com.jorgeolvr.servicoremessa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class PessoaFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @OneToOne(mappedBy = "pessoaFisica")
    private Usuario usuario;

    public PessoaFisica() {

    }

    public PessoaFisica(String cpf, Usuario usuario) {
       this.cpf = cpf;
       this.usuario = usuario;
    }

}
