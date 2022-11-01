package plataforma.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.admin.models.FechaInscripcion;
import plataforma.admin.repository.FechaInscripcionRepository;

@Service
public class FechaInscripcionService {
    Logger logger = LoggerFactory.getLogger(FechaInscripcionService.class);
    FechaInscripcionRepository repository ;

    @Autowired
    FechaInscripcionService(FechaInscripcionRepository repository){this.repository = repository;
    }

    public FechaInscripcion guardarFechas(FechaInscripcion m){
        FechaInscripcion f;

        try {
             f = repository.findById(1).get();
        }catch(Exception e){
            return repository.save(m);
        }

        f.es_final = m.es_final;
        f.inscripcion_desde = m.inscripcion_desde;
        f.inscripcion_hasta = m.inscripcion_hasta;

        return repository.save(f);
    }
    public FechaInscripcion guardarHoras(FechaInscripcion m){
        FechaInscripcion f;

        try {
            f = repository.findById(1).get();
        }catch(Exception e){
            f = new FechaInscripcion();
            f.horas_edicion_notas = m.horas_edicion_notas;
            return repository.save(f);
        }
        f.horas_edicion_notas = m.horas_edicion_notas;

        return repository.save(f);
    }

    public FechaInscripcion getFechas(){
        return repository.findById(1).get();
    }
}
