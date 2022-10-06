package plataforma.admin.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        catedras.add(crearCatedra(1, 6, 1, 1));
        catedras.add(crearCatedra(2, 6, 2, 1));
        catedras.add(crearCatedra(3, 7, 3, 1));
        catedras.add(crearCatedra(1, 7, 5, 1));

        catedras.add(crearCatedra(2, 1, 11, 1));
        catedras.add(crearCatedra(3, 1, 13, 1));
        catedras.add(crearCatedra(3, 2, 15, 1));
        catedras.add(crearCatedra(1, 2, 12, 1));

        catedras.add(crearCatedra(1, 10, 6, 1));
        catedras.add(crearCatedra(2, 10, 7, 1));
        catedras.add(crearCatedra(2, 10, 8, 1));
        catedras.add(crearCatedra(1, 10, 9, 1));

        return catedras;

    }

    private Catedra crearCatedra(int turno, int profesor, int materia, int cuatri){
        Catedra catedra = new Catedra();
        catedra.turno = turnoService.getTurno(turno);
        catedra.materia = materiaService.getMateria(materia);
        catedra.profesor = usuarioService.getUsuario(profesor);
        catedra.cuatrimestre = cuatriService.getCuatrimestre(cuatri);
        catedra.id = new CatedraId(catedra.turno, catedra.profesor, catedra.materia, catedra.cuatrimestre);
        catedra = catedraService.guardarCatedra(catedra);

        return catedra;
    }
}
