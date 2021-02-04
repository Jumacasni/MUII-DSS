package modelo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@XmlRootElement
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	
	public Producto(){
	}
	
	public Producto(int id, String nombre, double precio){
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
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
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getPrecio() {
		return this.precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	@Override
	public String toString() {
		return "Producto "+this.id+": "+this.nombre+", "+this.precio+"€";
	}
}
