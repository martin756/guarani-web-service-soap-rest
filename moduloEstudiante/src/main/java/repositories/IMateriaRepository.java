package repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Materia;





@Repository("materiaRepository")
public interface IMateriaRepository extends JpaRepository<Materia, Serializable>{
	
	public abstract Materia findByMateria(String Materia);
	
	
}
