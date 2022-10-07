package plataforma.admin.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "catedra")
public class Catedra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;


    public Catedra() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turno_id")
    public Turno turno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    public Usuario profesor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="materia_id")
    public Materia materia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cuatrimestre_id")
    public Cuatrimestre cuatrimestre;

    @Column(name = "es_final")
    public boolean es_final;


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
