package plataforma.admin.models;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="turno")
public class Turno {
    @Id
    @Column(name = "id")
    public int id;
    public Horario horario;

    @OneToMany(mappedBy = "turno")
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
