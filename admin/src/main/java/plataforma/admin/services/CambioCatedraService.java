package plataforma.admin.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.models.CambioCatedra;
import plataforma.admin.models.Catedra;
import plataforma.admin.repository.CambioCatedraRepository;
import plataforma.admin.requestModels.CambioCatedraResponse;

@Service
public class CambioCatedraService {
    Logger logger = LoggerFactory.getLogger(CambioCatedraService.class);

    CambioCatedraRepository repository ;
    @Autowired CatedraService catedraService;
    @Autowired InscripcionService inscripcionService ;

    @Autowired
    CambioCatedraService(CambioCatedraRepository cambioCatedraRepository){this.repository = cambioCatedraRepository;}

    public List<CambioCatedraResponse> getCambioCatedraPedendiente(){       
        List<CambioCatedra> result = new ArrayList<>();
        repository.getCambioCatedraPendiente().forEach(result::add);
   
        List<CambioCatedraResponse> cambioResponse = new ArrayList<>();
        for (CambioCatedra r: result) {
            Catedra c =catedraService.getCatedra(r.catedraNueva);
            Inscripcion i =inscripcionService.findInscripcionById(r.inscripcion);   
            String estudiante= i.estudiante.nombre +" "+  i.estudiante.apellido;
            String catedraActual = "TURNO: "+ i.catedra.turno.horario + " DIA: "+i.catedra.dia.dia +" CUATRIMESTRE: "+ i.catedra.cuatrimestre.periodo;
            String catedraSolicitada = "TURNO: "+ c.turno.horario + " DIA: "+ c.dia.dia +" CUATRIMESTRE: "+ c.cuatrimestre.periodo; 
            cambioResponse.add(new CambioCatedraResponse(r.id,estudiante, i.catedra.materia.nombre,catedraActual, c.materia.nombre,catedraSolicitada ));
        }


        return cambioResponse;
    }

    public CambioCatedra getSolicitud(int id){       
        return repository.findById(id).get();   
    }

    public void updateSolicitud(CambioCatedra c){
        repository.save(c);
    }
 
}