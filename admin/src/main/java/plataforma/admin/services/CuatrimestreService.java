package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Cuatrimestre;
import plataforma.admin.repository.CuatrimestreRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuatrimestreService {

    Logger logger = LoggerFactory.getLogger(CuatrimestreService.class);
    CuatrimestreRepository repository ;

    @Autowired
    CuatrimestreService(CuatrimestreRepository cuatrimestreRepository){
        this.repository = cuatrimestreRepository;
    }

    public List<Cuatrimestre> getCuatrimestres(){
        List<Cuatrimestre> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Cuatrimestre u: result) {
            logger.info("Cuatrimestre "+ u.toString());
        }
        return result;
    }

    public int guardarCuatrimestre(Cuatrimestre u) {
        return repository.save(u).id;
    }

    public Cuatrimestre getCuatrimestre(int id){
        return repository.findById(id).get();
    }
}
