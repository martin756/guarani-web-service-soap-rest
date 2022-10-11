package repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Carrera;





@Repository("carreraRepository")
public interface ICarreraRepository extends JpaRepository<Carrera, Serializable>{
	
	public abstract Carrera findByCarrera(String Carrera);
	
	
}
