package plataforma.admin.EstudianteModels;

import javax.persistence.*;
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

    public Date fecha_carga;

    public Nota() {
    }

}
