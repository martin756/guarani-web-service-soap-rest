package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table (name = "carrera")


public class Carrera {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="carrera")
	@NotEmpty
	private String nombre;
	
	



public Carrera(){
	
}
public Carrera(int id, String nombre) {
	this.id=id;
	this.nombre=nombre;
	
	
	
	
	}
		public int getId() {
			return id;
		}
		public void setId(int id) {
	this.id = id;
		}
	public String getNombre() {
	return nombre;
	}
	public void setNombre(String nombre) {
	this.nombre = nombre;
	}
@Override
public String toString() {
	return "Carrera [id=" + id +  ", carrera=" + nombre + "]";
}



}