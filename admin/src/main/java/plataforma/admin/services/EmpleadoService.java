package plataforma.admin.services;


import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface EmpleadoService {

    @WebMethod
    public String helloworld();

    @WebMethod
    public String hi(String fullname);
}
