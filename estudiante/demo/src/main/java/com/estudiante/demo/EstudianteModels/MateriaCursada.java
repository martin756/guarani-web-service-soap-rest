package com.estudiante.demo.EstudianteModels;

import java.util.ArrayList;
import java.util.List;

//Guarda la lista de inscripciones de un alumno a una materia
//sean cursadas, abandonadas o completas
//finales aprobados o desaprobados
public class MateriaCursada {

    public String nombre;

    public List<Inscripcion> cursada;
    public List<Inscripcion> finales;
    public List<Inscripcion> generales;

    public float promedio_cursada;
    public float promedio_finales;
    public float nota_final;

    public MateriaCursada() {
    }

    public MateriaCursada(List<Inscripcion> generales) {
        this.generales = generales;

        setCursadasYFinales();
        setNombreMateria();
        promedio_cursada();
        promedio_finales();
        setNota_final();
    }

    public void setCursadasYFinales(){
        List<Inscripcion> listaCursada = new ArrayList<>();
        List<Inscripcion> listaFinales = new ArrayList<>();
        for(Inscripcion i:generales){
            if( i.catedra.es_final){
                listaFinales.add(i);
            }else{
                listaCursada.add(i);
            }
        }
        cursada = listaCursada;
        finales = listaFinales;
    }

    public void promedio_cursada(){
        float i = 0;
        float contador = 0;
        promedio_cursada = 0;
        for(Inscripcion c : cursada){
            try {
                c.getPromedio();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i += c.promedio;
            contador ++;
        }
        contador = contador == 0 ? 1: contador;
        promedio_cursada = i / contador;

    }

    public void promedio_finales(){
        float i = 0;
        float contador = 0;
        promedio_finales = 0;
        for(Inscripcion f : finales){
            i += f.getNotas().get(0).nota;
            contador ++;
        }
        contador = contador == 0 ? 1: contador;
        promedio_finales = i/contador;

    }

    public void setNota_final(){
        if(promedio_cursada == 0){
            if(promedio_finales == 0){
                nota_final = 0;
            }else{
                nota_final = promedio_finales;
            }
        }else{
            if(promedio_finales == 0){
                nota_final = 0;
            }else{
                nota_final = (promedio_cursada + promedio_finales)/2;
            }
        }
    }

    public void setNombreMateria(){
        //El nombre de la materia sera el de la primer cursada, si no hay chequeo nombre de la materia de final
        nombre = cursada.get(0).catedra.materia.nombre != null ? cursada.get(0).catedra.materia.nombre : finales.get(0).catedra.materia.nombre;
    }
}

