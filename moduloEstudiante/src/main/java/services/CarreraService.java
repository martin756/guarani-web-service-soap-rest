package services;



import org.springframework.stereotype.Service;

import entities.Carrera;
import services.implementation.ICarreraService;



@Service
public interface CarreraService {
	public Carrera traerDatos(String Carrera);
	
}
