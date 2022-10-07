package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.*;
import plataforma.admin.services.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CatedraController {

    @Autowired CatedraService catedraService;
    @Autowired CuatrimestreService cuatrimestreService;
    @Autowired UsuarioService usuarioService;
    @Autowired MateriaService materiaService;
    @Autowired TurnoService turnoService;

    Logger logger = LoggerFactory.getLogger(CatedraController.class);

    @GetMapping("/catedra/{idcatedra}")
    public Catedra get(@PathVariable int idcatedra){
        Catedra result = catedraService.getCatedra(idcatedra);
        logger.info("Catedra obtenida"+ result.toString());
        return result;
    }

    @GetMapping("/catedra")
    public List<Catedra> getAll(){
        return catedraService.getAllCatedras();
    }

    @PostMapping("/catedra") //configurar validaciones
    public int crear(@RequestBody Catedra entidad ){
        logger.info("parseando catedra ");
        entidad.cuatrimestre = cuatrimestreService.getCuatrimestre(entidad.cuatrimestre.id);
        entidad.materia = materiaService.getMateria(entidad.materia.id);
        entidad.profesor = usuarioService.getUsuario(entidad.profesor.id);
        entidad.turno = turnoService.getTurno(entidad.turno.id);
        return catedraService.guardarCatedra(entidad).id;
    }


}
