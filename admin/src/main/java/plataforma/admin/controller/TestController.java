package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import plataforma.admin.models.*;
import plataforma.admin.services.*;
import java.util.List;


@RestController
public class TestController {

    @Autowired UsuarioService usuarioService;
    @Autowired CuatrimestreService cuatriService;
    @Autowired CarreraService carreraService;
    @Autowired TurnoService turnoService;
    @Autowired CatedraService catedraService;
    @Autowired MateriaService materiaService;
    @Autowired Generador generador;

    Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/generacionDatos")
    public List<Catedra> generacionDatos(){
        //Cuatrimestre
        Cuatrimestre cuatri = new Cuatrimestre();
        cuatri.anio = 2022;
        cuatri.periodo = 2;
        cuatriService.guardarCuatrimestre(cuatri);
        //Carreras con materias
        generador.getCarreras();
        //Usuarios
        List<Usuario> users = generador.generarUsuarios();
        for (Usuario u: users) {
            usuarioService.guardarUsuario(u);
            logger.info("Usuario "+ u.toString());
        }
        //Turnos
        List<Turno> turnos = turnoService.getTurnos();

        return generador.generarCatedras();
    }



}
