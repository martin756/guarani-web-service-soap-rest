package plataforma.admin.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Horario;
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
//        llenarTurnos();
        List<Turno> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Turno u: result) {
            logger.info("Turno "+ u.toString());
        }
        return result;
    }

    public Turno getTurno(int id){
//        llenarTurnos();
        return repository.findById(id).get();
    }

    private void llenarTurnos(){
        if(!hayTurnos()){
            Turno turno = new Turno(Horario.MANIANA,1);
            repository.save(turno);
            logger.info("guardando turno "+ turno);
            turno = new Turno(Horario.TARDE,2);
            repository.save(turno);
            logger.info("guardando turno "+ turno);
            turno = new Turno(Horario.NOCHE,3 );
            repository.save(turno);
            logger.info("guardando turno "+ turno);

        }
    }

    private boolean hayTurnos(){
        List<Turno> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        int size = result.size();
        return size != 0;
    }

}
