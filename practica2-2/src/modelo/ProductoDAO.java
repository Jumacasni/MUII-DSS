package modelo;

import java.util.HashMap;
import java.util.Map;

public enum ProductoDAO {
	INSTANCE;
	private Map<Integer, Producto> proveedorContenidos = new HashMap<Integer, Producto>();
	
	private ProductoDAO() {
		//Creamos 2 contenidos iniciales
		Producto producto = new Producto(1, "Mesa", 20);
		producto.setDescripcion("Este es el primer producto");
		proveedorContenidos.put(1, producto);
		producto = new Producto(2, "Silla", 20);
		producto.setDescripcion("Este es el segundo producto");
		proveedorContenidos.put(2, producto);
	}
	
	public Map<Integer, Producto> getModel(){
		
		return proveedorContenidos;
	}
}
