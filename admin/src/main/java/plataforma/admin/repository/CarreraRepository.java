package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Carrera;

@Repository
public interface CarreraRepository extends CrudRepository<Carrera, Integer> {


}
