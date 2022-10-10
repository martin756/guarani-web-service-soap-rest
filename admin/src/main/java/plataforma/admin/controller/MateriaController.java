package plataforma.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.models.Carrera;
import plataforma.admin.models.Materia;
import plataforma.admin.requestModels.MateriaRequest;
import plataforma.admin.services.CarreraService;
import plataforma.admin.services.MateriaService;
import javax.validation.Valid;
import java.util.List;

@RestController
public class MateriaController {

    @Autowired MateriaService materiaService;

    @Autowired CarreraService carreraService;

    Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @GetMapping("/materia/{id}")
    public Materia get(@PathVariable int id){
        Materia result = materiaService.getMateria(id);
        logger.info("Materia obtenida"+ result.toString());
        return result;
    }

    @GetMapping("/materia")
    public List<Materia> getAll(){
        return materiaService.getMaterias();
    }


    @PostMapping("/materia") //configurar validaciones
    public int crear(@Valid @RequestBody MateriaRequest entidad ){
        Materia materia = new Materia();
        materia.anio = entidad.anio;
        materia.nombre = entidad.nombre;
        logger.info("parseando materia "+entidad.nombre);
        Carrera c = carreraService.getCarrera(entidad.idCarrera);
        logger.info("parseando carrera "+c);
        materia.carrera = c;
        return materiaService.guardarMateria(materia);
    }

    @PostMapping("/materias") //configurar validaciones
    public int[] crearMultiples(@Valid @RequestBody MateriaRequest[] entidades ){
        int[] result = new int[entidades.length];
        int i = 0;
        for (MateriaRequest entidad: entidades) {
        Materia materia = new Materia();
        materia.anio = entidad.anio;
        materia.nombre = entidad.nombre;
        logger.info("parseando materia "+entidad.nombre);
        Carrera c = carreraService.getCarrera(entidad.idCarrera);
        logger.info("parseando carrera "+c);
        materia.carrera = c;
        result[i]= materiaService.guardarMateria(materia);
        i++;
        }
        return result;

    }

    @PutMapping("/materia/{id}") //configurar validaciones
    public int update(@PathVariable int id,@Valid @RequestBody MateriaRequest entidad ){
        Materia materiaToBeUpdated = materiaService.getMateria(id);
        materiaToBeUpdated.nombre = entidad.nombre == null ? materiaToBeUpdated.nombre : entidad.nombre;
        materiaToBeUpdated.anio = entidad.anio == 0 ? materiaToBeUpdated.anio : entidad.anio;
        return materiaService.guardarMateria(materiaToBeUpdated);
    }



}
