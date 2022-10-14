package plataforma.admin.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;
    public String nombre;
    public String apellido;
    public int dni;
    public TipoUsuario tipoUsuario;
    @JsonIgnore
    private String usuario;
    @JsonIgnore
    public String password;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, int dni, TipoUsuario tipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.tipoUsuario = tipo;
        setUsuario(nombre, apellido);
        setPassword(Integer.toString(dni));
    }

    public void setUsuario(String name, String apellido){

        this.usuario = name+apellido;
    }

    public void setPassword(String password){
        this.password = sha1(password);
    }

    static String sha1(String input){
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public void setCredenciales(){
        if (this.usuario == null || this.password == null){
            setUsuario(this.nombre, this.apellido);
            setPassword(Integer.toString(this.dni));
        }
    }



    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", tipoUsuario=" + tipoUsuario +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
