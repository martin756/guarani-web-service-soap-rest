package plataforma.reporte.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import plataforma.reporte.Service.PdfService;
import plataforma.reporte.model.Catedra;
import plataforma.reporte.model.ExcelBase64;

@RestController
public class PdfController {
    @Autowired
    PdfService pdfService;

    @GetMapping("pdfMateriasCuatrimestre")
    public ExcelBase64 pdfMateriasCuatrimestre(int idTurno) throws IOException, DocumentException {

        Catedra[] response=  pdfService.requestCatedra("catedra/turno/"+idTurno);
        ExcelBase64 result = new ExcelBase64(pdfService.CrearPdf(pdfService.tablaMaterias(response), "MATERIAS POR CUATRIMESTRE:","materiasPdf.pdf"));

        return result;
    }

    @GetMapping("pdfFinales")
    public ExcelBase64 pdfFinales() throws IOException, DocumentException {

        Catedra[] response=  pdfService.requestCatedra("/mesa");      
        ExcelBase64 result = new ExcelBase64(pdfService.CrearPdf(pdfService.tablaFinales(response), "FINALES:", "finalesPdf.pdf"));
        return result;
    }


  
}
