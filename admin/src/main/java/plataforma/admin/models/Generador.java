package plataforma.admin.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Generador {

    public static List<Usuario> generarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Jorge", "Mandela", 111, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("Rafael", "Porsche", 222, TipoUsuario.DOCENTE));
        usuarios.add(new Usuario("Roger", "LeClerk", 333, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Ayrton", "Bottas", 444, TipoUsuario.ESTUDIANTE));
        usuarios.add(new Usuario("Max", "Prost", 555, TipoUsuario.ESTUDIANTE));
        return usuarios;
    }

    public static Cuatrimestre generarCuatrimestre(){
        Cuatrimestre cuatri = new Cuatrimestre();
        cuatri.periodo = 2;
        cuatri.anio = 2022;
        return cuatri;
    }

    public static Carrera getCarrera(){
        return new Carrera("Emfermeria");
    }




}
