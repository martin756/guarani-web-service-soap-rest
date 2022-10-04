package plataforma.admin.services;

import javax.xml.ws.*;


public class Main {
    public static void main(String[] args) {
        try{

            Endpoint.publish("http://localhost:8081/empleadoservice", new Empleado());

            System.out.printf("ok");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
