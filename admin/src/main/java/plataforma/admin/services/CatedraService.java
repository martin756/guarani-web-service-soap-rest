package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.Catedra;
import plataforma.admin.repository.CatedraRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatedraService {
    Logger logger = LoggerFactory.getLogger(CatedraService.class);
    CatedraRepository repository ;

    @Autowired
    CatedraService(CatedraRepository usuarioRepository){
        this.repository = usuarioRepository;
    }

    public List<Catedra> getAllUsuarios(){
        List<Catedra> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        for (Catedra u: result) {
            logger.info("Usuario "+ u.toString());
        }
        return result;
    }

    public void guardarCatedra(Catedra u) {
        repository.save(u);
    }

    public Catedra getCatedra(int id){
        return repository.findById(id).get();
    }

}
