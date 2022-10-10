package plataforma.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plataforma.admin.EstudianteModels.Inscripcion;
import plataforma.admin.models.Catedra;

import java.util.List;

@Repository
public interface InscripcionRepository extends CrudRepository<Inscripcion, Integer>  {

    @Query("SELECT i FROM Inscripcion i WHERE i.catedra.id =(:idCatedra)")
    public abstract List<Inscripcion> getInscripcionesCatedra(int idCatedra);
}
