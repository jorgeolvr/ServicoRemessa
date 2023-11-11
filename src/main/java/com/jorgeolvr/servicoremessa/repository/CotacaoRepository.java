package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.Cotacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotacaoRepository extends CrudRepository<Cotacao, Long> {
    Cotacao findTopByOrderByIdDesc();
}
