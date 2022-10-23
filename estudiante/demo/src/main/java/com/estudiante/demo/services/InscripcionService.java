package com.estudiante.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estudiante.demo.EstudianteModels.Inscripcion;
import com.estudiante.demo.repositories.InscripcionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscripcionService {
    

    Logger logger = LoggerFactory.getLogger(InscripcionService.class);
    InscripcionRepository repository ;

    @Autowired
    InscripcionService(InscripcionRepository repository){
        this.repository = repository;
    }

    public List<Inscripcion> getInscripcionesACatedra(int idCatedra){
        return repository.getInscripcionesCatedra(idCatedra);
    }

    public Inscripcion guardar(Inscripcion i){
        return repository.save(i);
    }

    public Inscripcion findInscripcionById(int id){
        return repository.findById(id).get();
    }

    public List<Inscripcion> findByEstudiante(int id){
        return repository.getInscripcionesDeEstudiante(id);
    }

        public List<Inscripcion> getAllInscripciones(){
        List<Inscripcion> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Inscripcion u: result) {
            logger.info("Inscripcion "+ u.toString());
        }
        return result;
    }

        public void deleteInscripcion(int i, int j) {

            int id =repository.getIdInscripcion(i,j);
            logger.info("Inscripcion id ="+ id);
            repository.deleteById(id);
            // repository.deleteInscripcionDeEstudiante(id);
        }


}
