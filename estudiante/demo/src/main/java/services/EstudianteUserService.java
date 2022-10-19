package services;


import java.util.Optional;

import entities.EstudianteUser;

public interface EstudianteUserService {

	public EstudianteUser findById(int id);
	public void actualizarUsuario(int id);
	public EstudianteUser traerId(int id);
	public EstudianteUser saveOrUpdate(int id, String direccion, String email, String password );
	
	
}
