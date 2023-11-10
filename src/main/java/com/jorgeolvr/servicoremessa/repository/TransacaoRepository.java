package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.Transacao;
import com.jorgeolvr.servicoremessa.enums.TipoMovimentacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
    @Query("SELECT t FROM Transacao t WHERE t.usuario.id = :id")
    List<Transacao> findByUsuarioId(@Param("id") Long id);

    @Query("SELECT t FROM Transacao t WHERE t.tipoMovimentacao = :tipoMovimentacao")
    List<Transacao> findByTipoMovimentacao(@Param("tipoMovimentacao") TipoMovimentacao tipoMovimentacao);
}
