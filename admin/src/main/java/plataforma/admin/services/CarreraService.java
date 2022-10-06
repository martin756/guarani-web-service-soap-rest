package plataforma.admin.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Carrera;
import plataforma.admin.repository.CarreraRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarreraService {
    Logger logger = LoggerFactory.getLogger(CarreraService.class);
    CarreraRepository repository ;

    @Autowired
    CarreraService(CarreraRepository carreraRepository){this.repository = carreraRepository;}

    public int guardarCarrera(Carrera c){
        logger.info("info de carrera "+ c );
        return repository.save(c).id_carrera;
    }

    public Carrera getCarrera(int id){
        return repository.findById(id).get();
    }

    public void updateCarrera(Carrera c){
        repository.save(c);
    }



    public List<Carrera> getCarreras(){
        List<Carrera> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }
}
