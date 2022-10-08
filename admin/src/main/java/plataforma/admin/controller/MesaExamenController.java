package plataforma.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.Catedra;
import plataforma.admin.requestModels.MesaRequest;
import plataforma.admin.services.*;

import java.util.List;

@RestController
public class MesaExamenController {
    @Autowired
    CatedraService catedraService;
    @Autowired
    CuatrimestreService cuatrimestreService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MateriaService materiaService;
    @Autowired
    TurnoService turnoService;


    Logger logger = LoggerFactory.getLogger(CatedraController.class);

    @GetMapping("/mesa/{idmesa}")
    public Catedra get(@PathVariable int idmesa){
        Catedra result = catedraService.findMesaById(idmesa);
        logger.info("Catedra obtenida"+ result.toString());
        return result;
    }

    @GetMapping("/mesa")
    public List<Catedra> getAll(){
        return catedraService.findMesas();
    }

    @PostMapping("/mesa") //configurar validaciones
    public int crear(@RequestBody MesaRequest entidad ){
        logger.info("parseando mesa ");
        Catedra catedra = new Catedra();
        catedra.cuatrimestre = cuatrimestreService.getCuatrimestre(entidad.idCuatrimestre);
        catedra.materia = materiaService.getMateria(entidad.idMateria);
        catedra.profesor = usuarioService.getUsuario(entidad.idProfesor);
        catedra.turno = turnoService.getTurno(entidad.idTurno);
        catedra.es_final = true;
        catedra.fecha_final = entidad.fecha;
        return catedraService.guardarCatedra(catedra).id;
    }

}
