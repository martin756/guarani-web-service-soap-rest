package plataforma.reporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import plataforma.reporte.Service.CatedraExcelGenerador;
import plataforma.reporte.Service.ExcelService;
import plataforma.reporte.model.*;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ExcelController {
    @Autowired
    ExcelService excelService;

    @Autowired
    CatedraExcelGenerador catedraExcel;


    @GetMapping("inscripcionesCatedra/{id}")
    public ExcelBase64 getCatedra(@PathVariable int id) throws IOException {
        Root[] response = excelService.requestCatedra(id);
        //Ejemplo que la columna de generacion de notas
//        ArrayList<Float> notas = new ArrayList<>() ;
//        notas.add(5.3f);
//        notas.add(4.5f);
//        notas.add(8.7f);
//        ArrayList<Float> notas1 = new ArrayList<>() ;
//        notas1.add(3.1f);
//        notas1.add(6.2f);
//        notas1.add(9.9f);
//        response[0].notas = notas;
//        response[1].notas = notas1;

        ExcelBase64 result = new ExcelBase64(catedraExcel.generarExcel(response));

        return result;
    }

    @GetMapping("inscripcionesMesas/{id}")
    public ExcelBase64 getMesas(@PathVariable int id) throws IOException {
        Root[] response = excelService.requestCatedra(id);
        ArrayList<Float> notas = new ArrayList<>() ;
        notas.add(5.3f);
//        notas.add(4.5f);
//        notas.add(8.7f);
        ArrayList<Float> notas1 = new ArrayList<>() ;
        notas1.add(3.1f);
//        notas1.add(6.2f);
//        notas1.add(9.9f);
        response[0].notas = notas;
        response[1].notas = notas1;

        ExcelBase64 result = new ExcelBase64(catedraExcel.generarExcel(response));

        return result;
    }

}
