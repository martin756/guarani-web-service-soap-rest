package plataforma.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@Table(name="dia_semana")
public class DiaSemana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    public int id;

    @Column(name="dia")
    public Dia dia;

    public DiaSemana() {
    }
}


//@Entity
//@Table(name="turno")
//public class Turno {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    public int id;
//
//    @Column(name = "horario")
//    public Horario horario;
//
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turno")
//    @XmlTransient
//    public List<Catedra> catedras;
