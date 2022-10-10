package plataforma.admin.requestModels;

import plataforma.admin.models.TipoUsuario;

public class UsuarioRequest {
    public String nombre;
    public String apellido;
    public int dni;
    public TipoUsuario tipo;
    public String password;

    public UsuarioRequest() {
    }
}
