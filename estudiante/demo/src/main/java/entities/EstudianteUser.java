package entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "usuarios")
public class EstudianteUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "usuario")
	@NotEmpty
	private String user;

	@Column(name = "password")
	@NotEmpty
	private String password;
	
	@Column(name = "tipo_usuario")
	@NotEmpty
	private int tipo_usuario;
	
	@Column(name = "dni")
	@NotEmpty
	private int dni;

	@Column(name = "nombre")
	@NotEmpty
	private String nombre;

	@Column(name = "apellido")
	@NotEmpty
	private String apellido;

	@Column(name = "email")
	@NotEmpty(message = "El campo email no debe estar vacio")
	@Email
	private String email;

	@Column(name = "dirección")
	@NotEmpty(message = "El campo dirección no debe estar vacío")
	private String direccion;

	public EstudianteUser() {
		super();
	}


	public EstudianteUser(int id, @NotEmpty String user, @NotEmpty String pass, @NotEmpty int tipo_usuario, @NotEmpty int dni,
			@NotEmpty String nombre, @NotEmpty String apellido,
			@NotEmpty(message = "El campo email no debe estar vacio") @Email String email, 
			@NotEmpty(message = "El campo dirección no debe estar vacío") String direccion)
			 {
		this.id = id;
		this.user = user;
		this.password = password;
		this.tipo_usuario = tipo_usuario;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.direccion = direccion;
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(int tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	@Override
	public String toString() {
		return "EstudianteUser [id=" + id + ", user=" + user + ", pass=" + password + ", tipo_usuario="+ tipo_usuario +" dni=" + dni + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", email=" + email + ", direccion=" + direccion
				+ "]";
	}




	



}