package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.repository.InscripcionRepository;
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

    public List<Inscripcion> getAllInscripciones(){
        List<Inscripcion> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Inscripcion u: result) {
            logger.info("Inscripcion "+ u.toString());
        }
        return result;
    }
}
