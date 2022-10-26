package plataforma.reporte.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import plataforma.reporte.EstudianteModels.Analitico;
import plataforma.reporte.EstudianteModels.MateriaCursada;
import plataforma.reporte.ResponseModel.AnaliticoResponse;
import plataforma.reporte.services.CatedraService;
import plataforma.reporte.services.InscripcionService;
import plataforma.reporte.services.UsuarioService;
import  plataforma.reporte.ResponseModel.MateriaResponse;

public class AnaliticoService {
    
    @Autowired InscripcionService inscripcionService;
    @Autowired UsuarioService usuarioService;
    @Autowired CatedraService catedraService;
    
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
