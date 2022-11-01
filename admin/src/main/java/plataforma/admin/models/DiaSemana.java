package plataforma.admin.models;

import javax.persistence.*;

@Entity
@Table(name="dia_semana")
public class DiaSemana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name="dia")
    public Dia dia;

    public DiaSemana() {
    }
}

