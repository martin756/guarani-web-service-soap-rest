package plataforma.admin.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plataforma.admin.models.CambioCatedra;
import plataforma.admin.repository.CambioCatedraRepository;

@Service
public class CambioCatedraService {
    Logger logger = LoggerFactory.getLogger(CambioCatedraService.class);

    CambioCatedraRepository repository ;

    @Autowired
    CambioCatedraService(CambioCatedraRepository cambioCatedraRepository){this.repository = cambioCatedraRepository;}

    public List<CambioCatedra> getCambioCatedraPedendiente(){       
        List<CambioCatedra> result = new ArrayList<>();
        repository.getCambioCatedraPendiente().forEach(result::add);
        return result;
    }

    public CambioCatedra getSolicitud(int id){       
        return repository.findById(id).get();   
    }

    public void updateSolicitud(CambioCatedra c){
        repository.save(c);
    }
 
}