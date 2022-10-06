package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {



}
