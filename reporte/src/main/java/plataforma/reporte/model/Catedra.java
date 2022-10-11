package plataforma.reporte.model;

import java.util.Arrays;
import java.util.List;

public class Catedra {
    public int id;
    public Turno turno;
    public Profesor profesor;
    public Materia materia;
    public Cuatrimestre cuatrimestre;
    public Dia dia;
    public boolean es_final;
    public Object fecha_final;

    @Override
    public String toString() {
        return "Catedra{" +
                "id=" + id +
                ", turno=" + turno +
                ", profesor=" + profesor +
                ", materia=" + materia +
                ", cuatrimestre=" + cuatrimestre +
                ", dia=" + dia +
                ", es_final=" + es_final +
                ", fecha_final=" + fecha_final +
                '}';
    }
}
