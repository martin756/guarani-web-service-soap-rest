package repositories;

import javax.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.EstudianteUser;


@Repository("estudianteUserRepository")
public interface EstudianteUserRepository extends CrudRepository<EstudianteUser, Integer>{
 
	@Modifying
	@Transactional
	@Query("Update usuarios set email=(:email), direccion=(:direccion), password=(:password) Where id=(:id)")
	public abstract EstudianteUser update(@Param("id")int id, @Param("direccion")String direccion, @Param("email")String email, @Param("password")String password); 
}
