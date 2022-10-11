package services;



import org.springframework.stereotype.Service;

import entities.Carrera;
import services.implementation.CarreraService;



@Service
public interface ICarreraService {
	public Carrera traerDatos(String Carrera);
	
}
