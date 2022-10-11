package plataforma.reporte.model;

import java.util.ArrayList;

public class Root {
    public int id;
    public Catedra catedra;
    public Estudiante estudiante;
    public ArrayList<Float> notas;

    @Override
    public String toString() {
        return "Root{" +
                "id=" + id +
                ", catedra=" + catedra +
                ", estudiante=" + estudiante +
                ", notas=" + notas +
                '}';
    }
}
