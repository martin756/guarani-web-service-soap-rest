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
	private String carrera;
	
	



public Carrera(){
	
}
public Carrera(int id, String carrera) {
	this.id=id;
	this.carrera=carrera;
	
	
	
	
	}
		public int getId() {
			return id;
		}
		public void setId(int id) {
	this.id = id;
		}
	public String getCarrera() {
	return carrera;
	}
	public void setCarrera(String carrera) {
	this.carrera = carrera;
	}
@Override
public String toString() {
	return "Carrera [id=" + id +  ", carrera=" + carrera + "]";
}



}
