package plataforma.admin.requestModels;

public class CambioCatedraResponse {
    public int id;
    public String estudiante;
    public String materiaActual;
    public String catedraActual;
    public String materiaSolicitada;
    public String catedraSolicitada;


    public CambioCatedraResponse(int id, String estudiante, String materiaActual, String catedraActual,String materiaSolicitada,String catedraSolicitada) {
        this.id=id;
        this.estudiante=estudiante;
        this.materiaActual=materiaActual;
        this.catedraActual=catedraActual;
        this.materiaSolicitada=materiaSolicitada;
        this.catedraSolicitada=catedraSolicitada;
    }

    
}
