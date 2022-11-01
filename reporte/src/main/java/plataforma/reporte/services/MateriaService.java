package plataforma.reporte.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.reporte.models.Materia;
import plataforma.reporte.repositories.MateriaRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaService {
    Logger logger = LoggerFactory.getLogger(MateriaService.class);
    MateriaRepository repository ;

    @Autowired
    MateriaService(MateriaRepository materiaRepository){this.repository = materiaRepository;
    }

    public List<Materia> getMaterias(){
        List<Materia> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Materia u: result) {
            logger.info("Materia "+ u.toString());
        }
        return result;
    }

    public Materia getMateria(int id){
        return repository.findById(id).get();
    }

    public int guardarMateria(Materia m){
        Materia k = repository.save(m);
        return k.id;
    }
}
