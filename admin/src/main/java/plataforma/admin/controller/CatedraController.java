package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.*;
import plataforma.admin.requestModels.CatedraRequest;
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
    @Autowired DiaSemanaService diaSemanaService;


    Logger logger = LoggerFactory.getLogger(CatedraController.class);

    @GetMapping("/catedra/{idcatedra}")
    public Catedra get(@PathVariable int idcatedra){
        Catedra result = catedraService.findCatedraById(idcatedra);
        logger.info("Catedra obtenida"+ result.toString());
        return result;
    }

    @GetMapping("/catedra")
    public List<Catedra> getAll(){
        return catedraService.getAllCatedras();
    }

    @PostMapping("/catedra") //configurar validaciones
    public int crear(@RequestBody CatedraRequest entidad ){
        logger.info("parseando catedra ");
        Catedra catedra = new Catedra();
        catedra.cuatrimestre = cuatrimestreService.getCuatrimestre(entidad.idCuatrimestre);
        catedra.materia = materiaService.getMateria(entidad.idMateria);
        catedra.profesor = usuarioService.getUsuario(entidad.idProfesor);
        catedra.turno = turnoService.getTurno(entidad.idTurno);
        catedra.dia = diaSemanaService.getDia(entidad.diaSemana);
        return catedraService.guardarCatedra(catedra).id;
    }


}
