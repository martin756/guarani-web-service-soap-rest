package plataforma.reporte.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.reporte.models.Materia;

@Repository
public interface MateriaRepository extends CrudRepository<Materia, Integer> {
}
