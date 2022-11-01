package plataforma.reporte.EstudianteModels;

import plataforma.reporte.models.Catedra;
import plataforma.reporte.models.Usuario;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuario_materia_cuatrimestre")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcatedra")
    public Catedra catedra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario")
    public Usuario estudiante;

    @OneToMany(mappedBy="inscripcion", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    public List<Nota> nota_parciales;

    public float promedio;
    public float nota_final;

    float getPromedio() throws Exception{
        float i = 0;
        int contador = 0;
        for(Nota n : nota_parciales){
            i += n.nota;
            contador ++;
        }
        try{
            promedio = i/contador;
            if(catedra.es_final){
                nota_final = promedio;
            }else{
                nota_final = 0;
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return promedio;
    }

    public Inscripcion() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catedra getCatedra() {
        return this.catedra;
    }

    public void setCatedra(Catedra catedra) {
        this.catedra = catedra;
    }

    public Usuario getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public List<Nota> getNotas() {
        return this.nota_parciales;
    }

    public void setNotas(List<Nota> notas) {
        this.nota_parciales = notas;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", catedra='" + getCatedra() + "'" +
            ", estudiante='" + getEstudiante() + "'" +
            ", nota_parciales='" + getNotas() + "'" +
            "}";
    }
}