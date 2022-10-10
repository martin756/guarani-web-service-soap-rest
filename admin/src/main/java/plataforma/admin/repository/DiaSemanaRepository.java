package plataforma.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.models.DiaSemana;

@Repository
public interface DiaSemanaRepository extends CrudRepository<DiaSemana, Integer> {
}
