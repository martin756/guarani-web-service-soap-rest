package plataforma.reporte.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import  plataforma.reporte.EstudianteModels.Analitico;
import plataforma.reporte.EstudianteModels.MateriaCursada;
import  plataforma.reporte.ResponseModel.AnaliticoResponse;
import  plataforma.reporte.ResponseModel.MateriaResponse;
import  plataforma.reporte.services.CatedraService;
import  plataforma.reporte.services.InscripcionService;
import  plataforma.reporte.services.UsuarioService;

@RestController
public class TestController {


    @Autowired InscripcionService inscripcionService;
    @Autowired UsuarioService usuarioService;
    @Autowired CatedraService catedraService;

    Logger logger = LoggerFactory.getLogger(TestController.class);



    @GetMapping("/GetAnaliticoResponse/{id_estudiante}")
    public AnaliticoResponse RetornarAnalitico(@PathVariable int id_estudiante){
        return generarAnalitico(id_estudiante);
    }






    public AnaliticoResponse generarAnalitico(int id_estudiante){
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



}
