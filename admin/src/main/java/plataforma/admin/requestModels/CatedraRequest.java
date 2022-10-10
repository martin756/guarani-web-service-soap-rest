package plataforma.admin.requestModels;

import org.springframework.stereotype.Component;

@Component
public class CatedraRequest {
    public int idCuatrimestre;
    public int idProfesor;
    public int idTurno;
    public int idMateria;
    public int diaSemana;

    public CatedraRequest() {
    }
}
