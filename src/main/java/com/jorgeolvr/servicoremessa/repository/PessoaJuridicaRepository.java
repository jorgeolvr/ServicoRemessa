package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.PessoaJuridica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends CrudRepository<PessoaJuridica, Long> {
}
