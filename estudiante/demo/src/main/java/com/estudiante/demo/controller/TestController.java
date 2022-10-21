package com.estudiante.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudiante.demo.EstudianteModels.Analitico;
import com.estudiante.demo.EstudianteModels.Inscripcion;
import com.estudiante.demo.ResponseModel.AnaliticoResponse;
import com.estudiante.demo.ResponseModel.MateriaResponse;
import com.estudiante.demo.models.Catedra;
import com.estudiante.demo.models.Materia;
import com.estudiante.demo.models.Usuario;
import com.estudiante.demo.services.CatedraService;
import com.estudiante.demo.services.InscripcionService;
import com.estudiante.demo.services.UsuarioService;

@RestController
public class TestController {


    @Autowired InscripcionService inscripcionService;
    @Autowired UsuarioService usuarioService;
    @Autowired CatedraService catedraService;

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hola")
    public String getHola(){
        logger.info("Ver inscripciones");
        List<Inscripcion> inscripciones = inscripcionService.findByEstudiante(3);

        for(Inscripcion i : inscripciones){
            logger.info("iinscripcion" + i);
        }

         logger.info("Inscribir usuario");
        // Usuario usuario = usuarioService.getUsuario(3);
        // Catedra catedra = catedraService.getCatedra(6);
        // Inscripcion inscripcion = new Inscripcion();
        // inscripcion.estudiante = usuario;
        // inscripcion.catedra = catedra;
        // inscripcionService.guardar(inscripcion);

        logger.info("Desinscribir");
        // inscripcionService.deleteInscripcion(3,6);
        
        
        logger.info("Analitico");
            //recibo id estudiante
            //obtengo lista de inscripciones
            //itero por notas

        // logger.info("GetDatos");
        // Usuario usuarioAdevolver = usuarioService.getUsuario(3);

        // logger.info("Set Usuario");
        // Usuario usuario = new Usuario();//Va a venir por SOAP
        // int IDUsuarioPorWDSL = 3;
        //     Usuario usuarioToBeUpdated = usuarioService.getUsuario(3);
        //     String nuevaPassword = "";
        //     logger.info("usuario a ser actualizado "+usuarioToBeUpdated);
        //     //De ser necesario llamar a setCredenciales para actualizarlas con los cambios de datos
        //     usuarioToBeUpdated.nombre = usuario.nombre == null ? usuarioToBeUpdated.nombre : usuario.nombre;
        //     usuarioToBeUpdated.apellido = usuario.apellido == null ? usuarioToBeUpdated.apellido : usuario.apellido;
        //     usuarioToBeUpdated.dni = usuario.dni == 0 ? usuarioToBeUpdated.dni : usuario.dni;
        //     usuarioToBeUpdated.email = usuario.email == null ? usuarioToBeUpdated.email : usuario.email;
        //     usuarioToBeUpdated.direccion = usuario.direccion == null ? usuarioToBeUpdated.direccion : usuario.direccion;
        //     if (nuevaPassword != null){
        //         usuarioToBeUpdated.setPassword(nuevaPassword);
        //     }
        //     logger.info("usuario actualizado "+usuarioToBeUpdated);
        //     usuarioService.guardarUsuario(usuarioToBeUpdated);


        return "hola";
        
    }
    @GetMapping("/")
    public String helloworld(){
        return "hola";
    }

    @GetMapping("/GetAnalitico")
    public Analitico getAnalitico(){
        int idEstudiante = 3;
        Usuario usuario = usuarioService.getUsuario(idEstudiante);
        Analitico analitico = new Analitico();
        analitico.inscripciones = inscripcionService.findByEstudiante(idEstudiante);
        analitico.setPromedio();
        return analitico;
    }

    @GetMapping("/GetAnaliticoResponse")
    public AnaliticoResponse RetornarSoapAnalitico(){
        Analitico analitico = new Analitico();
        analitico.inscripciones = inscripcionService.findByEstudiante(3);
        analitico.setPromedio();
        //iterar por las inscripciones y guardar en
        AnaliticoResponse response = new AnaliticoResponse();
        
        List<MateriaResponse> materiasAnalitico = new ArrayList<MateriaResponse>();
        for(Inscripcion i : analitico.inscripciones){
            MateriaResponse materia = new MateriaResponse();
            materia.nombre = i.catedra.materia.nombre;
            materia.promedio = i.promedio;
            materiasAnalitico.add(materia);
        }
        response.materias = materiasAnalitico;
        response.promedioGeneral = analitico.promedio;

        return response;
        //poner en envelope soap y retornar

    }



}
