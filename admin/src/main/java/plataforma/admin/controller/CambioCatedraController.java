package plataforma.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/cambioEstadoSolicitud")
    public String cambioEstadoSolicitud(int id, String estado){
        CambioCatedra solicitud = cambioService.getSolicitud(id);  
        if(estado == "Aceptado"){
            //cambiar idCatedra en usuario_materia_cuatrimestre
            Inscripcion inscripcion =inscripcionService.findInscripcionById(solicitud.inscripcion.id);
            Catedra catedra = catedraService.getCatedra(solicitud.catedraNueva.id);            
            inscripcion.catedra= catedra;        
        }   
        solicitud.solicitud= estado;  
        cambioService.updateSolicitud(solicitud);
        return "ok";
    }



}
