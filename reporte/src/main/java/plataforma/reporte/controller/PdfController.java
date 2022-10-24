package plataforma.reporte.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import plataforma.reporte.EstudianteModels.Analitico;
import plataforma.reporte.EstudianteModels.MateriaCursada;
import plataforma.reporte.ResponseModel.AnaliticoResponse;
import plataforma.reporte.ResponseModel.MateriaResponse;
import plataforma.reporte.Service.AnaliticoService;
import plataforma.reporte.Service.PdfService;
import plataforma.reporte.model.Catedra;
import plataforma.reporte.model.ExcelBase64;
import plataforma.reporte.services.CatedraService;
import plataforma.reporte.services.InscripcionService;
import plataforma.reporte.services.UsuarioService;

@RestController
public class PdfController {
    @Autowired PdfService pdfService;
    @Autowired InscripcionService inscripcionService;
    @Autowired UsuarioService usuarioService;
    @Autowired CatedraService catedraService;


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

    @GetMapping("/pdfAnalitico/{id_estudiante}")
    public ExcelBase64 RetornarAnalitico(@PathVariable int id_estudiante) throws IOException, DocumentException{
        AnaliticoResponse analitoco =  generarAnalitico(id_estudiante);
        ExcelBase64 result = new ExcelBase64(pdfService.pdfAnalitico(analitoco));
        return result;
    }

  

    public AnaliticoResponse generarAnalitico(int id_estudiante){
        Analitico analitico = new Analitico(inscripcionService.findByEstudiante(id_estudiante));
        analitico.filtrarInscripciones();
        analitico.setPromedio_carrera();
        //iterar por las inscripciones y guardar en
        AnaliticoResponse response = new AnaliticoResponse();

        List<MateriaResponse> materiasAnalitico = new ArrayList<>();
        for(MateriaCursada i : analitico.inscripciones){
            MateriaResponse materia = new MateriaResponse();
            materia.nombre = i.nombre;
            materia.promedio_cursada = i.promedio_cursada;
            materia.nota_final = i.nota_final;
            materia.final_cursada = i.promedio_finales;
            materiasAnalitico.add(materia);
        }
        response.materias = materiasAnalitico;
        response.promedioGeneral = analitico.promedio_carrera;
        return response;
    }
  
}
