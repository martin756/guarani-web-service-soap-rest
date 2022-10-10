package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import plataforma.admin.models.Usuario;
import plataforma.admin.services.UsuarioService;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping("/usuario/{id}")
    public Usuario get(@PathVariable int id){
        Usuario result = usuarioService.getUsuario(id);
        logger.info("Usuario obtenido"+ result.toString());
        return result;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getAll(){
        return usuarioService.getAllUsuarios();
    }

    @PostMapping("/usuario")
    public int crear(@RequestBody Usuario usuario){
        usuario.setCredenciales();
        logger.info("parseando usuario "+usuario.toString());
        return usuarioService.guardarUsuario(usuario);
    }

    @PostMapping("/usuario/{id}")
    public int update(@PathVariable int id, @RequestBody Usuario usuario){
        Usuario usuarioToBeUpdated = usuarioService.getUsuario(id);
        logger.info("usuario a ser actualizado "+usuarioToBeUpdated);
        //De ser necesario llamar a setCredenciales para actualizarlas con los cambios de datos
        usuarioToBeUpdated.nombre = usuario.nombre == null ? usuarioToBeUpdated.nombre : usuario.nombre;
        usuarioToBeUpdated.apellido = usuario.apellido == null ? usuarioToBeUpdated.apellido : usuario.apellido;
        usuarioToBeUpdated.dni = usuario.dni == 0 ? usuarioToBeUpdated.dni : usuario.dni;
        usuarioToBeUpdated.password = usuario.password == null ? usuarioToBeUpdated.password : usuario.password;
        //if(usuario.getPassword() != null){usuarioToBeUpdated.password = usuario.getPassword();}
        logger.info("usuario actualizado "+usuarioToBeUpdated);
        return usuarioService.guardarUsuario(usuarioToBeUpdated);
    }

    @GetMapping("/usuario")
    public Usuario getByUsernamePassword(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        Usuario result = usuarioService.getUsuarioByUsernameAndPassword(username, password);
        if(result == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "registro no encontrado");
        logger.info("Usuario obtenido"+ result.toString());
        return result;
    }


}
