package plataforma.reporte.Service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import plataforma.reporte.model.Catedra;
import plataforma.reporte.model.ExcelBase64;
import plataforma.reporte.model.Root;
import plataforma.reporte.model.RootList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ExcelService {
    Logger logger = LoggerFactory.getLogger(ExcelService.class);

    String puerto = "8080";

    Gson gson = new Gson();

    public Root[] requestCatedra(int idCatedra){
        String servicio = "getInscripcionesACatedra";
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:"+puerto+"/"+servicio+"/"+ idCatedra)).build();
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            logger.info(getResponse.body());

            Root[] catedra;
            catedra = gson.fromJson(getResponse.body(), Root[].class);
            return catedra;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public void enviarExcel(String excelCodificado){
////        ExcelBase64 excelBase64 = new ExcelBase64(excelCodificado);
//        String jsonPost = gson.toJson(excelCodificado);
//        logger.info(excelCodificado);
//        try {
//            HttpRequest postRequest = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:8080/excel"))
//                    .header("Content-Type" ,"application/octet-stream")
//                     .POST(HttpRequest.BodyPublishers.ofString(jsonPost)).build();
//            HttpClient httpClient = HttpClient.newHttpClient();
//
//            HttpResponse<String> getResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
//
//        } catch (URISyntaxException | IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }




}
