package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.Usuario;
import plataforma.admin.requestModels.UsuarioRequest;
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

    @GetMapping("/usuario")
    public List<Usuario> getAll(){
        return usuarioService.getAllUsuarios();
    }

    @PostMapping("/usuario")
    public int crear(@RequestBody UsuarioRequest usuario){
        Usuario nuevoUsuario = new Usuario(usuario.nombre, usuario.apellido, usuario.dni,usuario.tipo);
        logger.info("parseando usuario "+usuario);
        return usuarioService.guardarUsuario(nuevoUsuario);
    }

    @PostMapping("/usuarios")
    public int[] crearMultiples(@RequestBody UsuarioRequest[] usuarios){
        int[] result = new int[usuarios.length];
        int i = 0;
        for (UsuarioRequest usuario: usuarios) {
            Usuario nuevoUsuario = new Usuario(usuario.nombre, usuario.apellido, usuario.dni,usuario.tipo);
            logger.info("parseando usuario "+usuario);
            result[i] = usuarioService.guardarUsuario(nuevoUsuario);
            i++;
        }
        return result;
    }



    @PutMapping("/usuario/{id}")
    public int update(@PathVariable int id, @RequestBody UsuarioRequest usuario){
        Usuario usuarioToBeUpdated = usuarioService.getUsuario(id);
        logger.info("usuario a ser actualizado "+usuarioToBeUpdated);
        //De ser necesario llamar a setCredenciales para actualizarlas con los cambios de datos
        usuarioToBeUpdated.nombre = usuario.nombre == null ? usuarioToBeUpdated.nombre : usuario.nombre;
        usuarioToBeUpdated.apellido = usuario.apellido == null ? usuarioToBeUpdated.apellido : usuario.apellido;
        usuarioToBeUpdated.dni = usuario.dni == 0 ? usuarioToBeUpdated.dni : usuario.dni;
        logger.info("usuario actualizado "+usuarioToBeUpdated);
        return usuarioService.guardarUsuario(usuarioToBeUpdated);
    }




}
