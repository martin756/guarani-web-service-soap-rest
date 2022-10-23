package com.estudiante.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.estudiante.demo.models.Catedra;

import java.util.List;

@Repository
public interface CatedraRepository extends CrudRepository<Catedra, Integer> {

    @Query("SELECT c FROM Catedra c WHERE c.es_final= True")
    public abstract List<Catedra> findMesas();

    @Query("SELECT c FROM Catedra c WHERE c.es_final = False")
    public abstract List<Catedra> getAllCatedras();

    @Query("SELECT c FROM Catedra c WHERE c.es_final = False and c.id=(:id)")
    public abstract Catedra findCatedraById(int id);

    @Query("SELECT c FROM Catedra c WHERE c.es_final = True and c.id=(:id)")
    public abstract Catedra findMesaById(int id);

    @Query("SELECT c FROM Catedra c WHERE c.es_final = (:esFinal) and c.turno.id=(:idTurno) order by c.materia.carrera.id_carrera, c.materia.anio")
    public abstract List<Catedra> getAllCatedrasTurno(boolean esFinal, int idTurno);


}
