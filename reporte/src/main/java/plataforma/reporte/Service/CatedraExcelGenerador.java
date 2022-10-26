package plataforma.reporte.Service;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plataforma.reporte.model.Estudiante;
import plataforma.reporte.model.Root;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CatedraExcelGenerador {

    @Autowired
    ExcelService excelService;

    public String generarExcel(Root[] response)throws IOException {
        Workbook wb= new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Catedra "+response[0].catedra.id);

        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("idEstudiante");
        row0.createCell(1).setCellValue("Nombre");
        row0.createCell(2).setCellValue("Apellido");
        row0.createCell(3).setCellValue("Dni");
        int rowNum = 1;
            for(Root r: response){
                Estudiante e = r.estudiante;
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(e.id);
                row.createCell(1).setCellValue(e.nombre);
                row.createCell(2).setCellValue(e.apellido);
                row.createCell(3).setCellValue(e.dni);
                
                int cantNotas = 0;
                if(response[0].catedra.es_final){
                    for (Float nota: r.notas) {
                        row0.createCell(4+cantNotas).setCellValue("Nota Final");
                        row.createCell(4+cantNotas).setCellValue(nota);
                        cantNotas++;
                    }
                }else{
                    for (Float nota: r.notas) {
                        row0.createCell(4+cantNotas).setCellValue("Nota "+(cantNotas+1));
                        row.createCell(4+cantNotas).setCellValue(nota);
                        cantNotas++;
                    }
                }

            }
            if(response[0].catedra.es_final){
                row0.createCell(4).setCellValue("Nota Final");
                row0.createCell(8).setCellValue("Fecha Final");
                String fecha = ((String)response[0].catedra.fecha_final).substring(0,10);
                row0.createCell(9).setCellValue(fecha);
            }else{
                row0.createCell(4).setCellValue("Nota");
                row0.createCell(5).setCellValue("NroParcial");
                row0.createCell(8).setCellValue("Dias");
                row0.createCell(9).setCellValue(response[0].catedra.dia.dia);
            }

        row0.createCell(10).setCellValue("Catedra id:");
        row0.createCell(11).setCellValue(response[0].catedra.id);
        row0.createCell(12).setCellValue("Materia:");
        row0.createCell(13).setCellValue(response[0].catedra.materia.nombre);
        row0.createCell(14).setCellValue("Profesor:");
        row0.createCell(15).setCellValue(response[0].catedra.profesor.apellido+" "+ response[0].catedra.profesor.nombre);




        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] excelEnBytes = baos.toByteArray();
        byte[] encodeBytes = Base64.encodeBase64(excelEnBytes);

//        excelService.enviarExcel(base64String);



        return  Base64.encodeBase64String(excelEnBytes);

    }
}
