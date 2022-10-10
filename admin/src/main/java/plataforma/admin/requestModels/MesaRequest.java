package plataforma.admin.requestModels;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MesaRequest {
        public int idCuatrimestre;
        public int idProfesor;
        public int idTurno;
        public int idMateria;
        public Date fecha;


        public MesaRequest() {
        }

}
