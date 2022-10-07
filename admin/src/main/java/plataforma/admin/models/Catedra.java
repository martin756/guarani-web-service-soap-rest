package plataforma.admin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@XmlRootElement(name="catedra")
@Entity
@Table(name = "catedra")
//@IdClass(CatedraKey.class)
public class Catedra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;
//    @EmbeddedId
//    public CatedraId id;

//    @Id
//    public int id_turno;
//    @Id
//    public int id_profesor;
//    @Id
//    public int id_cuatrimestre;
//    @Id
//    public int id_materia;


    public Catedra() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turno_id")
    public Turno turno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarios_id")
    public Usuario profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="materia_id")
    public Materia materia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cuatrimestre_id")
    public Cuatrimestre cuatrimestre;

    @Column(name = "es_final")
    public boolean es_final;

    //aniadir relacion con inscripciones


//    public Materia getMateria() {
//        return materia;
//    }
//
//    public void setMateria(Materia materia) {
//        this.materia = materia;
//    }
//
//    public Turno getTurno() {
//        return turno;
//    }
//
//    public void setTurno(Turno turno) {
//        this.turno = turno;
//    }
//
//    public Usuario getProfesor() {
//        return profesor;
//    }
//
//    public void setProfesor(Usuario profesor) {
//        this.profesor = profesor;
//    }

    @Override
    public String toString() {
        return "Catedra{" +
                " turno=" + turno +
                ", profesor=" + profesor +
                ", materia=" + materia +
                ", cuatrimestre=" + cuatrimestre +
                '}';
    }
}
