package modelo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@XmlRootElement
public class Carrito {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private Producto producto;
	private int cantidad;
	
	public Carrito(){
	}
	
	public Carrito(int id, Producto producto, int cantidad){
		this.id = id;
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public int getCantidad() {
		return this.cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
