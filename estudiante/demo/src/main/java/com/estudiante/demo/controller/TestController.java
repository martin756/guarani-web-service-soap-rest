package com.estudiante.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estudiante.demo.EstudianteModels.Analitico;
import com.estudiante.demo.EstudianteModels.Inscripcion;
import com.estudiante.demo.EstudianteModels.MateriaCursada;
import com.estudiante.demo.ResponseModel.AnaliticoResponse;
import com.estudiante.demo.ResponseModel.MateriaResponse;
import com.estudiante.demo.models.Catedra;
import com.estudiante.demo.models.Materia;
import com.estudiante.demo.models.Usuario;
import com.estudiante.demo.models.UsuarioUpdate;
import com.estudiante.demo.services.CatedraService;
import com.estudiante.demo.services.InscripcionService;
import com.estudiante.demo.services.UsuarioService;

@RestController
public class TestController {


    @Autowired InscripcionService inscripcionService;
    @Autowired UsuarioService usuarioService;
    @Autowired CatedraService catedraService;


    Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("inscripcion/{id_estudiante}")
    public List<Inscripcion> getInscripciones(@RequestParam int id_estudiante){
        return inscripcionService.findByEstudiante(id_estudiante);
    }
    
    @GetMapping("inscripcion/{id_estudiante}/{id_catedra}")
    public void inscripcion(@RequestParam int id_estudiante,@RequestParam int id_catedra){
        Usuario usuario = usuarioService.getUsuario(id_estudiante);
        Catedra catedra = catedraService.getCatedra(id_catedra);
        if(inscripcionService.sePuedeInscribir(id_estudiante, id_catedra)){
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.estudiante = usuario;
            inscripcion.catedra = catedra;
            inscripcionService.guardar(inscripcion);
        }
    }
    
    @DeleteMapping("inscripcion/{id_estudiante}/{id_catedra}")
    public void desInscripcion(@RequestParam int id_estudiante,@RequestParam int id_catedra){
        inscripcionService.deleteInscripcion(id_estudiante,id_catedra);
    }
    
    @GetMapping("analitico/{id_estudiante}")
    public AnaliticoResponse generarAnalitico(@RequestParam int id_estudiante){
        Analitico analitico = new Analitico(inscripcionService.findByEstudiante(id_estudiante));
        analitico.filtrarInscripciones();
        analitico.setPromedio_carrera();
        //iterar por las inscripciones y guardar en
        AnaliticoResponse response = new AnaliticoResponse();

        List<MateriaResponse> materiasAnalitico = new ArrayList<>();
        for(MateriaCursada i : analitico.inscripciones){
            MateriaResponse materia = new MateriaResponse();
            materia.nombre = i.nombre;
            materia.promedio_cursada = i.promedio_cursada;
            materia.nota_final = i.nota_final;
            materia.final_cursada = i.promedio_finales;
            materiasAnalitico.add(materia);
        }
        response.materias = materiasAnalitico;
        response.promedioGeneral = analitico.promedio_carrera;
        return response;
    }
   
    @GetMapping("usuario/{id_usuario}")
    public Usuario getUsuario(@RequestParam int id_usuario) {
        return usuarioService.getUsuario(id_usuario);
    }
   
    @PutMapping("usuario/{id_usuario}")
    public void actualizarUsuario(@RequestBody UsuarioUpdate usuario) {
                
                Usuario usuarioToBeUpdated = usuarioService.getUsuario(usuario.id_usuario);
                
                logger.info("usuario a ser actualizado "+usuarioToBeUpdated);
                //De ser necesario llamar a setCredenciales para actualizarlas con los cambios de datos
                usuarioToBeUpdated.nombre = usuario.nombre == null ? usuarioToBeUpdated.nombre : usuario.nombre;
                usuarioToBeUpdated.apellido = usuario.apellido == null ? usuarioToBeUpdated.apellido : usuario.apellido;
                usuarioToBeUpdated.dni = usuario.dni == 0 ? usuarioToBeUpdated.dni : usuario.dni;
                usuarioToBeUpdated.email = usuario.email == null ? usuarioToBeUpdated.email : usuario.email;
                usuarioToBeUpdated.direccion = usuario.direccion == null ? usuarioToBeUpdated.direccion : usuario.direccion;
                if (usuario.nuevaPassword != null){
                    usuarioToBeUpdated.setPassword(usuario.nuevaPassword);
                }
                logger.info("usuario actualizado "+usuarioToBeUpdated);
                usuarioService.guardarUsuario(usuarioToBeUpdated);

    }

   


}
