package plataforma.admin.EstudianteModels;

public class InscripcionResponse {
    public int id;
    public int idCatedra;
    public String materia;
    public String docente;
    public String horario;

    public InscripcionResponse(int id, int idCatedra, String materia, String docente,String horario){
        this.id=id;
        this.idCatedra=idCatedra;
        this.materia=materia;
        this.docente=docente;
        this.horario=horario;
    }
}
