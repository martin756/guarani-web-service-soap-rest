package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Turno;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, Integer> {

}
