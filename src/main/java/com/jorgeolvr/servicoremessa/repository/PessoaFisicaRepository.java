package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.PessoaFisica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {
}
