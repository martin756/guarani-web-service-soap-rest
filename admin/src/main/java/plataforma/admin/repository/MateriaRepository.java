package plataforma.admin.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Materia;

@Repository
public interface MateriaRepository extends CrudRepository<Materia, Integer> {
}
