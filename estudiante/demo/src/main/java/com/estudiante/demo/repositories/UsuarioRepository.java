package com.estudiante.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.estudiante.demo.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {



}
