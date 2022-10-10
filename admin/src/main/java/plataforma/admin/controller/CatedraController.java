package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.*;
import plataforma.admin.requestModels.CatedraRequest;
import plataforma.admin.requestModels.MesaRequest;
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
        Catedra catedra = getCatedra(entidad);
        return catedraService.guardarCatedra(catedra).id;
    }

    @PostMapping("/catedras") //configurar validaciones
    public int[] crearMultiples(@RequestBody CatedraRequest[] entidades ){
        int[] result = new int[entidades.length];
        int i = 0;
        logger.info("parseando catedra ");
        for (CatedraRequest entidad: entidades) {
            Catedra catedra = getCatedra(entidad);
            result[i]= catedraService.guardarCatedra(catedra).id;
            i++;
        }
        return result;
    }

    public Catedra getCatedra(CatedraRequest entidad) {
        Catedra catedra = new Catedra();
        catedra.cuatrimestre = cuatrimestreService.getCuatrimestre(entidad.idCuatrimestre);
        catedra.materia = materiaService.getMateria(entidad.idMateria);
        catedra.profesor = usuarioService.getUsuario(entidad.idProfesor);
        catedra.turno = turnoService.getTurno(entidad.idTurno);
        catedra.dia = diaSemanaService.getDia(entidad.diaSemana);
        return  catedra;
    }


    @GetMapping("/catedra/turno/{idTurno}")
    public List<Catedra> getReporte(@PathVariable int idTurno) {
        return catedraService.getCatedraByTurno(false,idTurno);
    }



}
