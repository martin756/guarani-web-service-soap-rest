package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Cuatrimestre;

@Repository
public interface CuatrimestreRepository extends CrudRepository<Cuatrimestre, Integer> {
}
