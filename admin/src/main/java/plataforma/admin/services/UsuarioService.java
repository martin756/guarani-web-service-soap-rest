package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Usuario;
import plataforma.admin.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService{

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    UsuarioRepository repository ;

    @Autowired
    UsuarioService(UsuarioRepository usuarioRepository){
        this.repository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios(){
       List<Usuario> result = new ArrayList<>();
       repository.findAll().forEach(result::add);
        for (Usuario u: result) {
            logger.info("Usuario "+ u.toString());
        }
       return result;
    }

    public int guardarUsuario(Usuario u) {
        return repository.save(u).id;
    }

    public Usuario getUsuario(int id){
        return repository.findById(id).get();
    }

    public Usuario getUsuarioByUsernameAndPassword(String username, String password){
        return repository.findByUsernameAndPassword(username, password);
    }
    }

