package repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import entities.EstudianteUser;

public interface IEstudianteUserRepository extends PagingAndSortingRepository<EstudianteUser, Long> {

	@Query("SELECT p FROM usuarios p WHERE p.dni = (:dni)")
	public abstract EstudianteUser findByDni(@Param("dni") int dni);

}
