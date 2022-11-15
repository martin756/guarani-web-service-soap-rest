package plataforma.admin.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cambio_catedra")
public class CambioCatedra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcambio_catedra")
    public int id;

    public CambioCatedra() {
    }
  
    @Column(name = "idusuario_materia_cuatrimestre")
    public int inscripcion;
 
    @Column(name = "idcatedra_nueva")
    public int catedraNueva;

    @Column(name="solicitud")
    public String solicitud;


    public CambioCatedra(int inscripcion, int catedraNueva, String solicitud) {
        this.inscripcion=inscripcion;
        this.catedraNueva=catedraNueva;
        this.solicitud=solicitud;
    }

    public int getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(int inscripcion) {
        this.inscripcion = inscripcion;
    }

    public int getCatedraNueva() {
        return catedraNueva;
    }

    public void setCatedraNueva(int catedraNueva) {
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
                " inscripcion=" + inscripcion +
                  ", catedra_nueva=" + catedraNueva +
                ", solicitud=" + solicitud +
                '}';
    }
}




