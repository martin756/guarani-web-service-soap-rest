package plataforma.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="fechas_inscripciones")
public class FechaInscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    public int id;
    public Date inscripcion_desde;
    public Date inscripcion_hasta;
    public int horas_edicion_notas;
    public boolean es_final;

    public FechaInscripcion() {
    }

    @Override
    public String toString() {
        return "FechaInscripcion{" +
                "id=" + id +
                ", inscripcion_desde=" + inscripcion_desde +
                ", inscripcion_hasta=" + inscripcion_hasta +
                ", horas_edicion_notas=" + horas_edicion_notas +
                ", es_final=" + es_final +
                '}';
    }
}
