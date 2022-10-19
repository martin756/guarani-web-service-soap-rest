package com.estudiante.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.estudiante.demo.EstudianteModels.Inscripcion;
import com.estudiante.demo.models.Catedra;

import java.util.List;

@Repository
public interface InscripcionRepository extends CrudRepository<Inscripcion, Integer>  {

    @Query("SELECT i FROM Inscripcion i WHERE i.catedra.id =(:idCatedra)")
    public abstract List<Inscripcion> getInscripcionesCatedra(int idCatedra);

    @Query("SELECT i FROM Inscripcion i WHERE i.estudiante.id = (:idEstudiante)")
    public abstract List<Inscripcion> getInscripcionesDeEstudiante(int idEstudiante);

    @Query("SELECT i.id FROM Inscripcion i WHERE i.estudiante.id = (:idEstudiante) and i.catedra.id = (:idCatedra)")
    public abstract int getIdInscripcion(int idEstudiante, int idCatedra);

    // @Query("DELETE i FROM Inscripcion i WHERE i.id = (:idInscripcion)")
    // public abstract void deleteInscripcionDeEstudiante(int idInscripcion);


}
