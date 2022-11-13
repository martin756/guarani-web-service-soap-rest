package plataforma.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.models.CambioCatedra;
import plataforma.admin.models.Catedra;
import plataforma.admin.services.CambioCatedraService;
import plataforma.admin.services.CatedraService;
import plataforma.admin.services.InscripcionService;

@RestController
public class CambioCatedraController {
   
    @Autowired CambioCatedraService cambioService;
    @Autowired InscripcionService inscripcionService;
    @Autowired CatedraService catedraService;

    @GetMapping("/cambioCatedra")
    public List<CambioCatedra> getCambioCatedraPedendiente(){
        List<CambioCatedra> result = cambioService.getCambioCatedraPedendiente();      
        return result;
    }

    //get inscripciones x id 

    //get catedra id

    @GetMapping("/inscripcion/{id}")
    public Inscripcion getInscripcionById(@PathVariable int id){
        Inscripcion result = inscripcionService.findInscripcionById(id);      
        return result;
    }

    @PostMapping("/cambioEstadoSolicitud")
    public String cambioEstadoSolicitud(@RequestBody int id, String estado){
        CambioCatedra solicitud = cambioService.getSolicitud(id);  
        if(estado.equals("Aceptado")){
            Inscripcion inscripcion =inscripcionService.findInscripcionById(solicitud.inscripcion);
            Catedra catedra = catedraService.getCatedra(solicitud.catedraNueva);            
            inscripcion.catedra= catedra; 
            inscripcionService.guardar(inscripcion);       
        }   
        solicitud.solicitud= estado;  
        cambioService.updateSolicitud(solicitud);
        return "ok";
    }



}
