package plataforma.reporte.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import plataforma.reporte.EstudianteModels.Analitico;
import plataforma.reporte.ResponseModel.AnaliticoResponse;
import plataforma.reporte.ResponseModel.MateriaResponse;
import plataforma.reporte.model.Catedra;
import plataforma.reporte.models.Usuario;


@Service
public class PdfService {
    Logger logger = LoggerFactory.getLogger(PdfService.class);

    
    String puerto = "8080";

    Gson gson = new Gson();

    public Catedra[] requestCatedra(String servicio){
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:"+puerto+"/"+servicio)).build();
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            logger.info(getResponse.body());

            Catedra[] catedra;
            catedra = gson.fromJson(getResponse.body(), Catedra[].class);
            return catedra;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String CrearPdf(PdfPTable tabla, String titulo, String archivo) throws IOException, DocumentException {// Establecer el tamaño de página
        Document documento = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(documento,baos).setInitialLeading(20);

        documento.open();
        documento.add(new Paragraph(titulo));

        documento.add(new Phrase(Chunk.NEWLINE));
        documento.add(new Phrase(Chunk.NEWLINE));

        documento.add(tabla);
        documento.close();

        byte[] pdfEnBytes = baos.toByteArray();
        return  Base64.encodeBase64String(pdfEnBytes);

    }

    public PdfPTable tablaMaterias(Catedra[] catedras) throws IOException, DocumentException {// Establecer el tamaño de página

        Font fuente = new Font();
        fuente.setSize(8);

        PdfPTable tabla = new PdfPTable(7);
        tabla.addCell(new Paragraph("CARRERA",fuente));
        tabla.addCell(new Paragraph("TURNO",fuente));
        tabla.addCell(new Paragraph("PROFESOR",fuente));
        tabla.addCell(new Paragraph("MATERIA",fuente));
        tabla.addCell(new Paragraph("CUATRIMESTRE",fuente));
        tabla.addCell(new Paragraph("AÑO",fuente));
        tabla.addCell(new Paragraph("DIA",fuente));
 
         
        for (Catedra catedra:catedras)
        {
             tabla.addCell(new Paragraph(catedra.materia.carrera.nombre,fuente));
             tabla.addCell(new Paragraph(catedra.turno.horario,fuente));
             tabla.addCell(new Paragraph(catedra.profesor.nombre +" "+ catedra.profesor.apellido,fuente));           
             tabla.addCell(new Paragraph(catedra.materia.nombre,fuente));
             tabla.addCell(new Paragraph(String.valueOf(catedra.cuatrimestre.periodo),fuente));              
             tabla.addCell(new Paragraph(String.valueOf(catedra.materia.anio),fuente));    
             tabla.addCell(new Paragraph(String.valueOf(catedra.dia.dia),fuente)); 
 
        }
        return tabla;     
    }

    public PdfPTable tablaFinales(Catedra[] catedras) throws IOException, DocumentException {// Establecer el tamaño de página

        Font fuente = new Font();
        fuente.setSize(8);
        PdfPTable tabla = new PdfPTable(6);
        tabla.addCell(new Paragraph("CARRERA",fuente));
        tabla.addCell(new Paragraph("MATERIA",fuente));
        tabla.addCell(new Paragraph("PROFESOR",fuente));
        tabla.addCell(new Paragraph("FECHA",fuente));
        tabla.addCell(new Paragraph("DIA",fuente));
        tabla.addCell(new Paragraph("TURNO",fuente));
 
        for (Catedra catedra:catedras)
        {
            tabla.addCell(new Paragraph(catedra.materia.carrera.nombre,fuente));
            tabla.addCell(new Paragraph(catedra.materia.nombre,fuente));
            tabla.addCell(new Paragraph(catedra.profesor.nombre +" "+ catedra.profesor.apellido,fuente));         
            tabla.addCell(new Paragraph(String.valueOf(catedra.fecha_final).substring(0,10),fuente));
            tabla.addCell(new Paragraph(String.valueOf(catedra.dia.dia),fuente)); 
            tabla.addCell(new Paragraph(String.valueOf(catedra.turno.horario),fuente));                
 
        }
        return tabla;
       
 
    }

    public String pdfAnalitico(Usuario user, AnaliticoResponse analitico) throws IOException, DocumentException {// Establecer el tamaño de página

        Font fuente = new Font();
        fuente.setSize(8);
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell(new Paragraph("MATERIA",fuente));
        tabla.addCell(new Paragraph("PROMEDIO CURSADA",fuente));
        tabla.addCell(new Paragraph("FINAL CURSADA",fuente));
        tabla.addCell(new Paragraph("NOTA FINAL",fuente));
 
        for (MateriaResponse materia:analitico.materias)
        {
            tabla.addCell(new Paragraph(materia.nombre , fuente));
            tabla.addCell(new Paragraph(materia.promedio_cursada ));
            tabla.addCell(new Paragraph(materia.final_cursada));         
            tabla.addCell(new Paragraph(materia.nota_final));                    
 
        }
        Workbook wb= new HSSFWorkbook();

        Document documento = new Document();
        FileOutputStream ficheroPdf = new FileOutputStream("analiticoPdf.pdf");
        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

        documento.open();
        documento.add(new Paragraph("ANALITICO DEL ESTUDIANTE: " + user.nombre +" "+ user.apellido +" DNI: "+ user.dni));

        documento.add(new Phrase(Chunk.NEWLINE));
        documento.add(new Phrase(Chunk.NEWLINE));

        documento.add(tabla);
        documento.add(new Phrase(Chunk.NEWLINE));
        documento.add(new Paragraph("PROMEDIO GENERAL:"+ analitico.promedioGeneral));
        documento.close();


      
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] pdfEnBytes = baos.toByteArray();
        byte[] encodeBytes = Base64.encodeBase64(pdfEnBytes);

        return  Base64.encodeBase64String(pdfEnBytes);
   
 
    }
}
