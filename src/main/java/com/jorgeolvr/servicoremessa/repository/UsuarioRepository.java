package com.jorgeolvr.servicoremessa.repository;

import com.jorgeolvr.servicoremessa.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
