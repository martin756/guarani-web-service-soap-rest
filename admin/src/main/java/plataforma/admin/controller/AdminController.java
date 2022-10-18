package plataforma.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import plataforma.admin.models.FechaInscripcion;

import plataforma.admin.services.FechaInscripcionService;

@RestController
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    FechaInscripcionService fechaInscripcionService;

    @GetMapping("/fechas_inscripcion")
    public FechaInscripcion getFechasInscripcion(){
    return fechaInscripcionService.getFechas();
    }

    @PutMapping("/fechas_inscripcion")
    public FechaInscripcion setFechaInscripcion(@RequestBody FechaInscripcion entidad){
        logger.info(""+entidad);
        return fechaInscripcionService.guardarFechas(entidad);
    }

    @PutMapping("/fechas_inscripcion/horas_edicion")
    public FechaInscripcion setHorasEdicion(@RequestBody FechaInscripcion entidad){
        logger.info(""+entidad);
        return fechaInscripcionService.guardarHoras(entidad);
    }

}
