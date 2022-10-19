package repositories;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.Analitico;

@Repository("analiticoRepository")
public interface AnaliticoRepository extends CrudRepository<Analitico, Integer>{

	
	@Query("SELECT AVG (notas) FROM Analitico Where IdUsuario=(:id)")
	public abstract int promedioNotas(@Param("id") int id);
	
	
	

}
