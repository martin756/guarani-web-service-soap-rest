package plataforma.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.EstudianteModels.InscripcionResponse;
import plataforma.admin.models.CambioCatedra;
import plataforma.admin.models.Catedra;
import plataforma.admin.requestModels.CambioCatedraResponse;
import plataforma.admin.services.CambioCatedraService;
import plataforma.admin.services.CatedraService;
import plataforma.admin.services.InscripcionService;

@RestController
public class CambioCatedraController {
   
    @Autowired CambioCatedraService cambioService;
    @Autowired InscripcionService inscripcionService;
    @Autowired CatedraService catedraService;

    @GetMapping("/cambioCatedra")
    public List<CambioCatedraResponse> getCambioCatedraPedendiente(){
        List<CambioCatedraResponse> result = cambioService.getCambioCatedraPedendiente();      
        return result;
    }
 


    @GetMapping("/inscripcion/{idUsuario}")
    public List<InscripcionResponse>  getInscripcionByIdUsuario(int idUsuario){
        List<InscripcionResponse> result = inscripcionService.findInscripcionByIdUsuario(idUsuario);      
        return result;
    }

    @PostMapping("/enviarSolicitud")
    public String enviarSolicitud(int idInscripcion, int idCatedraNueva){
        CambioCatedra solicitud = new CambioCatedra(idInscripcion, idCatedraNueva,"Pendiente");
        return "Solicitud N°:"+cambioService.save(solicitud);
   
    }


    @PostMapping("/cambioEstadoSolicitud")
    public String cambioEstadoSolicitud(int id, String estado){
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
