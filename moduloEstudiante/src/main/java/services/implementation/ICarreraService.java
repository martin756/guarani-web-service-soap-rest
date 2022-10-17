package services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import entities.Carrera;
import repositories.ICarreraRepository;
import services.CarreraService;
import services.implementation.ICarreraService;



@Service("carreraService")
public class ICarreraService implements CarreraService{
	
	@Autowired
	@Qualifier("carreraRepository")
	private ICarreraRepository carreraRepository;
	
	
	public Carrera traerDatos(String Carrera) {
		return carreraRepository.findByCarrera(Carrera);
	}
	
	
}