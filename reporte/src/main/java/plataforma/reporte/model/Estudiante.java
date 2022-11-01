package plataforma.reporte.model;

public class Estudiante{
    public int id;
    public String nombre;
    public String apellido;
    public int dni;
    public String tipoUsuario;
    public String usuario;

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}