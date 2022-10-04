package plataforma.reporte;

import plataforma.admin.services.*;

public class Main {
    public static void main(String[] args) {

        EmpleadoService_Service eService = new EmpleadoService_Service();
        EmpleadoService serviceSOAP = eService.getEmpleadoPort();

        System.out.println(serviceSOAP.hi("Me gusta el helado"));
        System.out.println(serviceSOAP.helloworld());

    }
}
