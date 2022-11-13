package plataforma.admin.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import plataforma.admin.EstudianteModels.Inscripcion;


@Entity
@Table(name = "cambio_catedra")
public class CambioCatedra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcambio_catedra")
    public int id;

    public CambioCatedra() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusuario_materia_cuatrimestre")
    public Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcatedra_nueva")
    public Catedra catedraNueva;

    @JoinColumn(name="solicitud")
    public String solicitud;


    // public CambioCatedra(int inscripcion, int catedraNueva, String solicitud) {
    //     this.inscripcion=inscripcion;
    //     this.catedraNueva=catedraNueva;
    //     this.solicitud=solicitud;
    // }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Catedra getCatedraNueva() {
        return catedraNueva;
    }

    public void setCatedraNueva(Catedra catedraNueva) {
        this.catedraNueva = catedraNueva;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }
    @Override
    public String toString() {
        return "cambio catedra{" +
                // " inscripcion=" + inscripcion +
                //   ", catedra_nueva=" + catedraNueva +
                ", solicitud=" + solicitud +
                '}';
    }
}