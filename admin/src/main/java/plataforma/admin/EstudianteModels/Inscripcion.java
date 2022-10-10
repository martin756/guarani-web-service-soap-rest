package plataforma.admin.EstudianteModels;

import plataforma.admin.models.Catedra;
import plataforma.admin.models.Usuario;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuario_materia_cuatrimestre")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "idcatedra")
    public Catedra catedra;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    public Usuario estudiante;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    public Set<Nota> notas;

    public Inscripcion() {
    }


}
