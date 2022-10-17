package services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import entities.Materia;
import repositories.IMateriaRepository;
import services.MateriaService;
import services.implementation.IMateriaService;



@Service("materiaService")
public class IMateriaService implements MateriaService{
	
	@Autowired
	@Qualifier("materiaRepository")
	private IMateriaRepository materiaRepository;
	
	
	public Materia traerDatos(String Materia) {
		return materiaRepository.findByMateria("");
	}
	
	
}
