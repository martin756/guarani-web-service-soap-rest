package plataforma.admin.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CatedraId implements Serializable {

    public int id_turno;
    public int id_profesor;
    public int id_cuatrimestre;
    public int id_materia;

    public CatedraId() {
    }

    public CatedraId(Turno turno,
                      Usuario profesor,
                      Materia materia,
                     Cuatrimestre cuatrimestre
    ){
        this.id_turno = turno.id;
        this.id_profesor = profesor.id;
        this.id_materia = materia.id;
        this.id_cuatrimestre = cuatrimestre.id;
    }

//    public CatedraKey(int turno, int usuario, int materia, int cuatrimestre){
//        this.idturno = turno;
//        this.idprofesor = usuario;
//        this.idmateria = materia;
//        this.idcuatrimestre = cuatrimestre;
//    }
}
