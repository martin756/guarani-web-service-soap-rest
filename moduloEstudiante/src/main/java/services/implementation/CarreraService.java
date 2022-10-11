package services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import entities.Carrera;
import repositories.ICarreraRepository;
import services.ICarreraService;
import services.implementation.CarreraService;



@Service("carreraService")
public class CarreraService implements ICarreraService{
	
	@Autowired
	@Qualifier("carreraRepository")
	private ICarreraRepository carreraRepository;
	
	
	public Carrera traerDatos(String Carrera) {
		return carreraRepository.findByCarrera(Carrera);
	}
	
	
}