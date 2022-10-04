package plataforma.admin.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "catedra")
public class Catedra implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    int id;
    @EmbeddedId
    public CatedraId id;

    @ManyToOne
    @JoinColumn(name = "idturno")
    public Turno turno;

    @ManyToOne
    @JoinColumn(name = "idprofesor")
    public Usuario profesor;

    @ManyToOne
    @JoinColumn(name="idmateria")
    public Materia materia;

    @ManyToOne
    @JoinColumn(name="idcuatrimestre")
    public Cuatrimestre cuatrimestre;

    //aniadir relacion con inscripciones


    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Catedra{" +
                ", turno=" + turno +
                ", profesor=" + profesor +
                ", materia=" + materia +
                ", cuatrimestre=" + cuatrimestre +
                '}';
    }
}
