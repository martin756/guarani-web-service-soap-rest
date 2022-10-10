package plataforma.admin.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.services.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class Generador {

    @Autowired UsuarioService usuarioService;
    @Autowired CuatrimestreService cuatriService;
    @Autowired CarreraService carreraService;
    @Autowired TurnoService turnoService;
    @Autowired CatedraService catedraService;
    @Autowired MateriaService materiaService;
    @Autowired DiaSemanaService diaSemanaService;
    @Autowired InscripcionService inscripcionService;

    public  List<Usuario> generarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Jorge", "Mandela", 111, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("Rafael", "Porsche", 222, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("Roger", "LeClerk", 333, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Ayrton", "Bottas", 444, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Max", "Prost", 555, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Albert", "Copperfield", 666, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("German", "Schmidt", 777, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("Franz", "Paul", 888, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Frederich", "Klein", 999, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Francis", "Bacon", 56412, TipoUsuario.DOCENTE));
        return usuarios;
    }

    public  Cuatrimestre generarCuatrimestre(){
        Cuatrimestre cuatri = new Cuatrimestre();
        cuatri.periodo = 2;
        cuatri.anio = 2022;
        return cuatri;
    }

    public  Carrera getCarrera(){
        return new Carrera("Emfermeria");
    }
    public  List<Materia> getCarreras(){

        List<Carrera> carreras = new ArrayList<>();
        List<Materia> materias = new ArrayList<>();
        carreras.add( new Carrera("Licenciatura en Sistemas"));
        carreras.add( new Carrera("Licenciatura en Audiovision"));
        carreras.add( new Carrera("Licenciatura en Juegos de Mesa"));



        materias.add(new Materia("Programacion 1", 1,carreras.get(0)));
        materias.add(new Materia("Matematica 1", 1,carreras.get(0)));
        materias.add(new Materia("Matematica 2", 2,carreras.get(0)));
        materias.add(new Materia("Estadisctica", 2,carreras.get(0)));
        materias.add(new Materia("Proyecto de Software", 4,carreras.get(0)));


        materias.add(new Materia("Teoria periodistica", 1,carreras.get(1)));
        materias.add(new Materia("Escritura 1", 1,carreras.get(1)));
        materias.add(new Materia("FIsica Cuantica", 5,carreras.get(1)));
        materias.add(new Materia("Pintar con los dedos", 3,carreras.get(1)));
        materias.add(new Materia("Curso photoshop", 4,carreras.get(1)));

        materias.add(new Materia("Teoria Ludica", 1,carreras.get(2)));
        materias.add(new Materia("Ajedrez", 2,carreras.get(2)));
        materias.add(new Materia("Damas 2", 3,carreras.get(2)));
        materias.add(new Materia("Poker abierto", 2,carreras.get(2)));
        materias.add(new Materia("Black Jack", 4,carreras.get(2)));

        for (Materia m: materias) {
            materiaService.guardarMateria(m);
        }
        return materias;
    }



    public List<Catedra> generarCatedras() {
        List<Catedra> catedras = new ArrayList<>();
        catedras.add(crearCatedra(1, 6, 1, 1, 2));
        catedras.add(crearCatedra(2, 6, 2, 1,3));
        catedras.add(crearCatedra(3, 7, 3, 1,4));
        catedras.add(crearCatedra(1, 7, 5, 1,5 ));

        catedras.add(crearCatedra(2, 1, 11, 1,3));
        catedras.add(crearCatedra(3, 1, 13, 1,2));
        catedras.add(crearCatedra(3, 2, 15, 1,4));
        catedras.add(crearCatedra(1, 2, 12, 1,3));

        catedras.add(crearCatedra(1, 10, 6, 1,2));
        catedras.add(crearCatedra(2, 10, 7, 1,1));
        catedras.add(crearCatedra(2, 10, 8, 1,1));
        catedras.add(crearCatedra(1, 10, 9, 1,3));

        return catedras;

    }



    private Catedra crearCatedra(int turno, int profesor, int materia, int cuatri, int dia){
        Catedra catedra = new Catedra();
        catedra.turno = turnoService.getTurno(turno);
        catedra.materia = materiaService.getMateria(materia);
        catedra.profesor = usuarioService.getUsuario(profesor);
        catedra.cuatrimestre = cuatriService.getCuatrimestre(cuatri);
        catedra.dia = diaSemanaService.getDia(dia);
//        catedra.id = new CatedraId(catedra.turno, catedra.profesor, catedra.materia, catedra.cuatrimestre);
        catedra.es_final = false;
        catedra = catedraService.guardarCatedra(catedra);
        return catedra;
    }

    public void generarInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        inscripciones.add(crearInscripcion(3,1));
        inscripciones.add(crearInscripcion(3,2));
        inscripciones.add(crearInscripcion(4,2));
        inscripciones.add(crearInscripcion(4,3));
        inscripciones.add(crearInscripcion(4,1));
        inscripciones.add(crearInscripcion(5,3));
        inscripciones.add(crearInscripcion(5,4));
        inscripciones.add(crearInscripcion(8,4));
        inscripciones.add(crearInscripcion(8,2));
        inscripciones.add(crearInscripcion(8,9));
        inscripciones.add(crearInscripcion(9,9));
        inscripciones.add(crearInscripcion(9,10));

        for (Inscripcion i : inscripciones) {
            inscripcionService.guardar(i);
        }

    }

    public Inscripcion crearInscripcion(int idUsuario, int idCatedra){
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.estudiante = usuarioService.getUsuario(idUsuario);
        inscripcion.catedra = catedraService.getCatedra(idCatedra);
        return inscripcion;
    }
}
