package entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "materia")

public class Materia {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "anio")
	@NotEmpty
	private int anio;

	@Column(name = "nombre")
	@NotEmpty
	private String nombre;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "carrera")
	private Carrera carrera;

	public Materia() {

	}

	public Materia(int id, int anio, String nombre, Carrera carrera) {
		this.id = id;
		this.anio = anio;
		this.nombre = nombre;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	@Override
	public String toString() {
		return "Materia [id=" + id + ", anio=" + anio + ", nombre=" + nombre + ", carrera=" + carrera + "]";
	}
	
	

}