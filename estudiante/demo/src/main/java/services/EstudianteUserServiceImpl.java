package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import entities.EstudianteUser;
import repositories.EstudianteUserRepository;

@Service("estudianteUserService")
public class  EstudianteUserServiceImpl implements EstudianteUserService{
	
	
	@Autowired
	@Qualifier("estudianteUserRepository")
	private EstudianteUserRepository repository;
	@Override
	public EstudianteUser findById(int id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	public void actualizarUsuario(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EstudianteUser traerId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EstudianteUser saveOrUpdate(int id, String direccion, String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
