package plataforma.admin.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;


@Entity
@Component
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;

    public String nombre;

    public int anio;

    @OneToMany(mappedBy="materia")
    public List<Catedra> catedras;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    public Carrera carrera;

    public int getId() {
        return id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Materia() {
    }

    public Materia(String nombre, int anio, Carrera carrera) {
        this.nombre = nombre;
        this.anio = anio;
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", carrera=" + carrera.nombre +
                ", carrera id=" + carrera.id_carrera +
                '}';
    }
}
