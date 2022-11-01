package com.estudiante.demo.models;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Component
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;

    @Size(min = 5, max = 200, message
            = "Nombre materia debe ser entre 5 y 200 letras")
    public String nombre;

    @Min(value = 1, message = "Anio debe estar entre 1ro y 5to")
    @Max(value = 5, message = "Anio debe estar entre 1ro y 5to")
    public int anio;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "id_carrera")
    public Carrera carrera;

    public int getId() {
        return id;
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
