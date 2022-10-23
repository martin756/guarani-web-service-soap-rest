package plataforma.reporte.models;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Component
@Table(name = "carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id_carrera;

    public String nombre;

//    @OneToMany(mappedBy="carrera", cascade = CascadeType.ALL)
//    public List<Materia> materias;

    public Carrera(String nombre) {
        this.nombre = nombre;
    }

    public Carrera() {
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "nombre='" + nombre + '\'' +
//                "materias " + materias +
                '}';
    }


}
