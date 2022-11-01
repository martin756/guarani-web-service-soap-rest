package plataforma.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Query ("from Usuario u where u.usuario = (:username) and u.password = (:password)")
    public Usuario findByUsernameAndPassword(String username, String password);
}
