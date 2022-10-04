package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.Catedra;

@Repository
public interface CatedraRepository extends CrudRepository<Catedra, Integer> {
}
