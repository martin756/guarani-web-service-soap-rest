package services;

import java.util.List;
import java.util.Optional;

import entities.EstudianteUser;

public interface EstudianteUserService {

	public EstudianteUser findByDni(int dni);
	public EstudianteUser create(EstudianteUser estudiante);
	List<EstudianteUser> listar();
	public void actualizarUsuario(int dni);
	public Optional<EstudianteUser> traerId(int id);
	public void delete(int id);
	public EstudianteUser saveOrUpdate(EstudianteUser estudianteUserModificado);
	public Optional<EstudianteUser> listarDNI(int dni);
	}
