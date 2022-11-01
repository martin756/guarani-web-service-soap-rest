package plataforma.reporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import plataforma.reporte.Service.CatedraExcelGenerador;
import plataforma.reporte.Service.ExcelService;
import plataforma.reporte.model.*;
import java.io.IOException;

@RestController
public class ExcelController {
    @Autowired
    ExcelService excelService;

    @Autowired
    CatedraExcelGenerador catedraExcel;


    @GetMapping("inscripcionesCatedra/{id}")
    public ExcelBase64 getCatedra(@PathVariable int id) throws IOException {
        Root[] response = excelService.requestCatedra(id);

        ExcelBase64 result = new ExcelBase64(catedraExcel.generarExcel(response));

        return result;
    }

    @GetMapping("inscripcionesMesas/{id}")
    public ExcelBase64 getMesas(@PathVariable int id) throws IOException {
        Root[] response = excelService.requestCatedra(id);

        ExcelBase64 result = new ExcelBase64(catedraExcel.generarExcel(response));

        return result;
    }

}
