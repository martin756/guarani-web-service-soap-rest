package com.estudiante.demo.EstudianteModels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.estudiante.demo.models.Materia;

public class Analitico {

    public List<MateriaCursada> inscripciones;

    public List<Inscripcion> inscripcionesGenerales;

    public float promedio_carrera;
    
    public Analitico() {
    }
    public Analitico(List<Inscripcion> inscripcionesGenerales) {
        this.inscripcionesGenerales = inscripcionesGenerales;
    }

    public void setPromedio_carrera(){
        float i = 0;
        float contador = 0;
        float nota;
        for(MateriaCursada m: inscripciones){
            nota = m.nota_final;
            if(nota != 0){
                i += nota;
                contador ++;
            }
        }
        promedio_carrera =  contador == 0 ? 0 : i/ contador;
    }

    public void filtrarInscripciones(){
        List<MateriaCursada> listaMaterias = new ArrayList<>();
        List<Inscripcion> listaInscripcion = new ArrayList<>();
        HashSet<Materia> materias = new HashSet<>();
        for(Inscripcion i: inscripcionesGenerales){
            materias.add(i.catedra.materia);
        }
        for(Materia m: materias){
            for(Inscripcion i: inscripcionesGenerales){
                if(i.catedra.materia == m){
                    listaInscripcion.add(i);
                }
            }
            listaMaterias.add(new MateriaCursada(listaInscripcion));
            listaInscripcion = new ArrayList<>();
        }
        inscripciones = listaMaterias;
    }
}
