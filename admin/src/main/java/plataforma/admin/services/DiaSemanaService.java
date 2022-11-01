package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.DiaSemana;
import plataforma.admin.repository.DiaSemanaRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiaSemanaService {

    Logger logger = LoggerFactory.getLogger(DiaSemanaService.class);
    DiaSemanaRepository repository ;

    @Autowired
    DiaSemanaService(DiaSemanaRepository repository){
        this.repository = repository;
    }

    public DiaSemana getDia(int id){
        return repository.findById(id).get();
    }

    public List<DiaSemana> getDias(){
        List<DiaSemana> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (DiaSemana u: result) {
            logger.info("Dia "+ u.toString());
        }
        return result;
    }
}
