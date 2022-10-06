package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.Cuatrimestre;
import plataforma.admin.services.CuatrimestreService;
import java.util.List;

@RestController
public class CuatrimestreController {
    @Autowired CuatrimestreService cuatrimestreService;

    Logger logger = LoggerFactory.getLogger(CuatrimestreController.class);

    @GetMapping("/cuatrimestre/{id}")
    public Cuatrimestre get(@PathVariable int id){
        Cuatrimestre result = cuatrimestreService.getCuatrimestre(id);
        logger.info("cuatrimestre obtenido"+ result.toString());
        return result;
    }

    @GetMapping("/cuatrimestre")
    public List<Cuatrimestre> getAll(){
        return cuatrimestreService.getCuatrimestres();
    }

    @PostMapping("/cuatrimestre")
    public int crear(@RequestBody Cuatrimestre cuatrimestre){
        logger.info("parseando cuatrimestre "+cuatrimestre.toString());
        return cuatrimestreService.guardarCuatrimestre(cuatrimestre);
    }

    @PostMapping("/cuatrimestre/{id}")
    public int update(@PathVariable int id, @RequestBody Cuatrimestre cuatrimestre){
        Cuatrimestre cuatrimestreToBeUpdated = cuatrimestreService.getCuatrimestre(id);
        logger.info("cuatrimestre a ser actualizado "+cuatrimestreToBeUpdated);
        cuatrimestreToBeUpdated.periodo = cuatrimestre.periodo == 0 ? cuatrimestreToBeUpdated.periodo : cuatrimestre.periodo;
        cuatrimestreToBeUpdated.anio = cuatrimestre.anio == 0 ? cuatrimestreToBeUpdated.anio : cuatrimestre.anio;
        logger.info("cuatrimestre actualizado "+cuatrimestreToBeUpdated);
        return cuatrimestreService.guardarCuatrimestre(cuatrimestreToBeUpdated);
    }

}
