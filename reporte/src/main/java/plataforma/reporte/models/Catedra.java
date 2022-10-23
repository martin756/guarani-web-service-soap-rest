package plataforma.reporte.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="dia_semana_id")
    public DiaSemana dia;

    @Column(name = "es_final")
    public boolean es_final;

    @Column(name = "fecha_final")
    public Date fecha_final;


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
