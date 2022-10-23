package com.estudiante.demo.EstudianteModels;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Analitico {


    public List<Inscripcion> inscripciones;

    public float promedio;
    
    public Analitico() {
    }

    public void setPromedio(){
        int i = 0;
        int contador = 0;
        for(Inscripcion c : inscripciones){
            try {
                c.getPromedio();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i += c.promedio;
            contador ++;
        }
        promedio = i/contador;

    }



}
