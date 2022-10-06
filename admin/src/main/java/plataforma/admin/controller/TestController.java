package plataforma.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import plataforma.admin.models.*;
import plataforma.admin.services.*;
import plataforma.admin.services.Empleado;

import java.util.ArrayList;
import java.util.List;

import javax.jws.*;
import javax.xml.ws.*;


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

//    @GetMapping("/insertarAlumnos")
//    public void insertarAlumnos(){
//        List<Usuario> users = generador.generarUsuarios();
//        for (Usuario u: users) {
//            usuarioService.guardarUsuario(u);
//            logger.info("Usuario "+ u.toString());
//        }
//    }
//
//    @GetMapping("/crearMaterias")
//    public List<Materia> crearMaterias(){
//        return generador.getCarreras();
//    }
//
//
//
//    @GetMapping("/getUsuario/{id}")
//    public Usuario insertarAlumnos(@PathVariable int id){
//        Usuario user = usuarioService.getUsuario(id);
//        logger.info("Usuario obtenido"+ user.toString());
//        return user;
//
//    }

//    @GetMapping("/materias")
//    public List<Materia> getMaterias(){
//        Cuatrimestre cuatri = Generador.generarCuatrimestre();
//        cuatriService.guardarCuatrimestre(cuatri);
//        for (Materia m: cuatri.materias) {
//            logger.info(m.toString());
//        }
//        return cuatri.materias;
//    }

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

//    @GetMapping("/guardaMaterias")
//    public String guardaMaterias(){
//        Carrera carrera = generador.getCarrera();
//        logger.info(carrera.toString());
//        List<Materia> materias = new ArrayList<>();
//        materias.add(new Materia("Farmaco", 1, carrera));
//        materias.add(new Materia("Embrio", 2, carrera));
//        materias.add(new Materia("Gineco", 3, carrera));
////        carrera.materias = materias;
//        carreraService.guardarCarrera(carrera);
//        return "Hola";
//    }
//
//    @GetMapping("/updateMateria")
//    public String updateMateria(){
//        Carrera carrera = carreraService.getCarrera(5);
//        logger.info(carrera.toString());
//        carrera.nombre = "Medicina";
//        carreraService.updateCarrera(carrera);
//        return "se obtienen materias";
//    }
//
//    @GetMapping("/getTurnos")
//    public List<Turno> turnos(){
//        List<Turno> turnos = turnoService.getTurnos();
//        return turnos;
//    }
//
//    @GetMapping("/generarCuatri")
//    public String generarCuatri(){
//        Cuatrimestre cuatri = new Cuatrimestre();
//        cuatri.anio = 2022;
//        cuatri.periodo = 2;
//        cuatriService.guardarCuatrimestre(cuatri);
//        return "cuatriGenerado";
//    }

    @GetMapping("/generarCatedra")
    public String generarCatedra(){
        Catedra catedra = new Catedra();
        catedra.turno = turnoService.getTurno(1);
        catedra.materia = materiaService.getMateria(1);
        catedra.profesor = usuarioService.getUsuario(1);
        catedra.cuatrimestre = cuatriService.getCuatrimestre(1);
        catedra.id = new CatedraId(catedra.turno, catedra.profesor, catedra.materia, catedra.cuatrimestre);
        catedraService.guardarCatedra(catedra);

        return catedra.toString();
    }

//    @GetMapping("/catedra")
//    public String getCatedra(){
//        return catedraService.getCatedra(1).toString();
//    }

//    @GetMapping("/turnos")
//    public String getturnos(){
//        return turnoService.getTurno(1).toString();
//    }
//
//
//
//    @GetMapping("/cuatri")
//    public Cuatrimestre getCuatri(){
//        return generador.generarCuatrimestre();
//    }
//
//    @GetMapping("/helloworld")
//    public String helloworld(){
//        Endpoint.publish("http://localhost:8081/empleadoservice", new Empleado());
//
//        return "ok";
//    }





}
