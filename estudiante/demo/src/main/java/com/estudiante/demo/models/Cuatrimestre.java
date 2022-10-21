package com.estudiante.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Cuatrimestre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    public int periodo;

    public int anio;

//    @OneToMany(mappedBy="cuatrimestre")
//    public List<Catedra> catedras;

    public Cuatrimestre() {
    }

    @Override
    public String toString() {
        return "Cuatrimestre{" +
                "id=" + id +
                ", periodo=" + periodo +
                ", anio=" + anio +
//                ", materias=" + materias +
                '}';
    }
}
