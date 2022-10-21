package com.estudiante.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@Entity
@Table(name="turno")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "horario")
    public Horario horario;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turno")
    @XmlTransient
    public List<Catedra> catedras;

    public int getId() {
        return id;
    }

    public Turno() {
    }
    public Turno(Horario horario, int id) {
        this.id = id;
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", horario=" + horario +
                '}';
    }
}
