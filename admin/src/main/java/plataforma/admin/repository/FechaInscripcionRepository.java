package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.FechaInscripcion;

@Repository
public interface FechaInscripcionRepository extends CrudRepository<FechaInscripcion, Integer> {


}
