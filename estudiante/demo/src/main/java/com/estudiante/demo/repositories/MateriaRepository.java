package com.estudiante.demo.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.estudiante.demo.models.Materia;

@Repository
public interface MateriaRepository extends CrudRepository<Materia, Integer> {
}
