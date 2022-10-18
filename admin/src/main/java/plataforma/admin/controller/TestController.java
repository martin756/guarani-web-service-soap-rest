package plataforma.admin.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.models.*;
import plataforma.admin.services.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
public class TestController {

    @Autowired UsuarioService usuarioService;
    @Autowired CuatrimestreService cuatriService;
    @Autowired CarreraService carreraService;
    @Autowired TurnoService turnoService;
    @Autowired CatedraService catedraService;
    @Autowired MateriaService materiaService;
    @Autowired Generador generador;
    @Autowired InscripcionService inscripcionService;

    Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/generacionDatos")
    public List<Catedra> generacionDatos(){
        //Cuatrimestre
        Cuatrimestre cuatri = new Cuatrimestre();
        cuatri.anio = 2022;
        cuatri.periodo = 2;
        cuatriService.guardarCuatrimestre(cuatri);
        //Carreras con materias
        generador.getCarreras();
        //Usuarios
        List<Usuario> users = generador.generarUsuarios();
        for (Usuario u: users) {
            usuarioService.guardarUsuario(u);
            logger.info("Usuario "+ u.toString());
        }
        //Turnos
        List<Turno> turnos = turnoService.getTurnos();

        return generador.generarCatedras();
    }

    @GetMapping("/generarInscripciones")
    public List<Inscripcion> generarInscripciones(){
        generador.generarInscripciones();
        return inscripcionService.getAllInscripciones();
    }

    @GetMapping("/getInscripcionesACatedra/{idcatedra}")
    public List<Inscripcion> getInscripciones(@PathVariable int idcatedra){
        return inscripcionService.getInscripcionesACatedra(idcatedra);
    }


//    //Ejemplo dedoficicacion de excel codificado a string en Base64
//    @GetMapping("/excel")
//    public int getExcel(){
//        logger.info("parseand paquete de bytes");
//        String excel = "0M8R4KGxGuEAAAAAAAAAAAAAAAAAAAAAOwADAP7/CQAGAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAEAAAAgAAAAEAAAD+////AAAAAAEAAAD///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////9SAG8AbwB0ACAARQBuAHQAcgB5AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFgAFAf//////////AQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAACACAAAAAAAAFcAbwByAGsAYgBvAG8AawAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASAAIB////////////////AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGoIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP7////9/////v///wQAAAAFAAAABgAAAAcAAAD+////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////AQAAAAIAAAADAAAABAAAAAUAAAAGAAAABwAAAAgAAAAJAAAACgAAAAsAAAAMAAAADQAAAA4AAAAPAAAAEAAAABEAAAASAAAAEwAAABQAAAAVAAAAFgAAABcAAAAYAAAAGQAAABoAAAAbAAAAHAAAAB0AAAAeAAAAHwAAACAAAAAhAAAA/v////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////8JCBAAAAYFANMQzAdBAAAABgAAAOEAAgCwBMEAAgAAAOIAAABcAHAABwAAV2FsbGF0dCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIEIAAgCwBGEBAgAAAD0BAgAAAJwAAgAOABkAAgAAABIAAgAAABMAAgAAAK8BAgAAALwBAgAAAD0AEgBoAQ4BXDq+IzgAAAAAAAEAWAJAAAIAAACNAAIAAAAiAAIAAAAOAAIAAQC3AQIAAADaAAIAAAAxABUAyAAAAP9/kAEAAAAAAAAFAEFyaWFsMQAVAMgAAAD/f5ABAAAAAAAABQBBcmlhbDEAFQDIAAAA/3+QAQAAAAAAAAUAQXJpYWwxABUAyAAAAP9/kAEAAAAAAAAFAEFyaWFsHgQaAAUAFQAAIiQiIywjIzBfKTsoIiQiIywjIzApHgQfAAYAGgAAIiQiIywjIzBfKTtbUmVkXSgiJCIjLCMjMCkeBCAABwAbAAAiJCIjLCMjMC4wMF8pOygiJCIjLCMjMC4wMCkeBCUACAAgAAAiJCIjLCMjMC4wMF8pO1tSZWRdKCIkIiMsIyMwLjAwKR4ENQAqADAAAF8oIiQiKiAjLCMjMF8pO18oIiQiKiAoIywjIzApO18oIiQiKiAiLSJfKTtfKEBfKR4ELAApACcAAF8oKiAjLCMjMF8pO18oKiAoIywjIzApO18oKiAiLSJfKTtfKEBfKR4EPQAsADgAAF8oIiQiKiAjLCMjMC4wMF8pO18oIiQiKiAoIywjIzAu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";
//
//        byte[] encodeBytes = Base64.decodeBase64(excel);
//
//        try {
//            FileOutputStream out = new FileOutputStream("test.xls");
//            out.write(encodeBytes);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }




}
