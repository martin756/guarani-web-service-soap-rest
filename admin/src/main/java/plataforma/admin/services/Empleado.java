package plataforma.admin.services;

import javax.jws.*;

@WebService(endpointInterface = "plataforma.admin.services.EmpleadoService")
public class Empleado implements EmpleadoService{

    @Override
    public String helloworld(){
        return "Hello World";
    }

    @Override
    public String hi(String fullname) {
        return "Hello " + fullname;
    }
}
