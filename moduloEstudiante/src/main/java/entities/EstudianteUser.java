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
@Table(name = "estudianteUser")

public class EstudianteUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "usuario")
	@NotEmpty
	private String user;

	@Column(name = "contraseña")
	@NotEmpty
	private String pass;

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

	@Column(name = "teléfono")
	@NotEmpty(message = "El campo teléfono no debe estar vacio")
	private String telefono;

	@OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "carrera")
	private Carrera carrera;

	@Column(name = "dirección")
	@NotEmpty(message = "El campo dirección no debe estar vacío")
	private String direccion;

	@Column(name = "localidad")
	@NotEmpty(message = "El campo localidad no debe estar vacío")
	private String localidad;

	public EstudianteUser() {
		super();
	}


	public EstudianteUser(int id, @NotEmpty String user, @NotEmpty String pass, @NotEmpty int dni,
			@NotEmpty String nombre, @NotEmpty String apellido,
			@NotEmpty(message = "El campo email no debe estar vacio") @Email String email,
			@NotEmpty(message = "El campo teléfono no debe estar vacio") String telefono, Carrera carrera,
			@NotEmpty(message = "El campo dirección no debe estar vacío") String direccion,
			@NotEmpty(message = "El campo localidad no debe estar vacío") String localidad) {
		super();
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.telefono = telefono;
		this.carrera = carrera;
		this.direccion = direccion;
		this.localidad = localidad;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "EstudianteUser [id=" + id + ", user=" + user + ", pass=" + pass + ", dni=" + dni + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", email=" + email + ", telefono=" + telefono + ", carrera=" + carrera
				+ ", direccion=" + direccion + ", localidad=" + localidad + "]";
	}

}
