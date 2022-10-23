package plataforma.reporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

