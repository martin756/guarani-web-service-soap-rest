package services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import controllers.EstudianteUserController;
import entities.EstudianteUser;
import repositories.IEstudianteUserRepository;
import services.EstudianteUserService;

public class IEstudianteUserService implements EstudianteUserService {

	@Autowired
	private IEstudianteUserRepository estudianteUserRepository;

	@Override
	public EstudianteUser findByDni(@Param("dni") int dni) {
		return this.estudianteUserRepository.findByDni(dni);
	}

	@Override
	public EstudianteUser create(EstudianteUser estudiante) {
		return this.estudianteUserRepository.save(estudiante);
	}
	
	@Override
	public List<EstudianteUser> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<EstudianteUser> traerId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	public EstudianteUser saveOrUpdate(EstudianteUser estudianteUserNuevo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<EstudianteUser> listarDNI(int dni) {
		// TODO Auto-generated method stub
		return null;
	}

	public void actualizarUsuario(int dni) {
		// TODO Auto-generated method stub
		
	}

}