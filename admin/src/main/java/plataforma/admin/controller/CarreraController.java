package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.Carrera;
import plataforma.admin.services.CarreraService;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CarreraController {

    @Autowired
    CarreraService carreraService;

    Logger logger = LoggerFactory.getLogger(CarreraController.class);

    @GetMapping("/carrera/{id}")
    public Carrera get(@PathVariable int id){
        Carrera result = carreraService.getCarrera(id);
        logger.info("Materia obtenida"+ result.toString());
        return result;
    }

    @GetMapping("/carrera")
    public List<Carrera> getAll(){
        return carreraService.getCarreras();
    }

    @PostMapping("/carrera") //configurar validaciones
    public int crear(@Valid @RequestBody Carrera entidad ){
        logger.info("parseando carrera "+entidad.nombre);
        return carreraService.guardarCarrera(entidad);
    }

    @PostMapping("/carrera/{id}") //configurar validaciones
    public int update(@PathVariable int id,@Valid @RequestBody Carrera entidad ){
        Carrera carreraToBeUpdated = carreraService.getCarrera(id);
        carreraToBeUpdated.nombre = entidad.nombre == null ? carreraToBeUpdated.nombre : entidad.nombre;
        logger.info("parseando carrera "+entidad.nombre);
        return carreraService.guardarCarrera(carreraToBeUpdated);
    }

}
