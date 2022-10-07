package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Catedra;
import plataforma.admin.models.CatedraId;
import plataforma.admin.repository.CatedraRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatedraService {
    Logger logger = LoggerFactory.getLogger(CatedraService.class);
    CatedraRepository repository ;

    @Autowired
    CatedraService(CatedraRepository catedraRepository){
        this.repository = catedraRepository;
    }

    public List<Catedra> getAllCatedras(){
        List<Catedra> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Catedra u: result) {
            logger.info("Catedra "+ u.toString());
        }
        return result;
    }

    public Catedra guardarCatedra(Catedra u) {

        return repository.save(u);
    }

//    public Catedra getCatedra(CatedraId id){
//        return repository.findById(id).get();
//    }



    public Catedra getCatedra(int id){
        logger.info("obteniendo catedra de repositorio");
        Optional<Catedra> c = repository.findById(id);
        if(c.isPresent()){
            logger.info("hay catedra");
        }
        Catedra cate = c.get();
        System.out.println(cate);
        return cate;
    }

}
