package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.pessoaFisica.cpf = :cpf")
    Optional<Usuario> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.pessoaJuridica.cnpj = :cnpj")
    Optional<Usuario> findByCnpj(@Param("cnpj") String cnpj);
}
