package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Turno;
import plataforma.admin.repository.TurnoRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {

    Logger logger = LoggerFactory.getLogger(TurnoService.class);
    TurnoRepository repository ;

    @Autowired
    TurnoService(TurnoRepository turnoRepository){this.repository = turnoRepository;
    }

    public List<Turno> getTurnos(){
        List<Turno> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Turno u: result) {
            logger.info("Turno "+ u.toString());
        }
        return result;
    }

    public Turno getTurno(int id){
        return repository.findById(id).get();
    }
}
