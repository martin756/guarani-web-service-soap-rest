package plataforma.reporte.EstudianteModels;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

@Entity
@Table(name = "nota_parciales")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;

    @Column(name = "nota")
    public float nota;

    @ManyToOne
    @JoinColumn(name="idusuario_materia_cuatrimestre")
    @JsonIgnore
    public Inscripcion inscripcion;

    public Date fecha_carga;

    public Nota() {
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", nota='" + nota + "'" +
            ", fecha_carga='" + fecha_carga + "'" +
            "}";
    }
}
