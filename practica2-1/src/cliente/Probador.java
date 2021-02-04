package cliente;

import modelo.*;
import recurso.CatalogoRecurso;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

public class Probador {
	public static void main (String [ ] args) {
		ClientConfig config = new ClientConfig();
		Client cliente = ClientBuilder.newClient(config);
		WebTarget servicio = cliente.target(getBaseURI());
				
		// EL ADMINISTRADOR INSERTA CUATRO PRODUCTOS
		System.out.println("FORMULARIOS PARA INSERTAR PRODUCTOS EN EL CATALOGO:");
		System.out.println("Formulario para insertar el primer producto");
		Form form1 = new Form();
		form1.param("id", "1");
		form1.param("nombre", "Taburete");
		form1.param("descripcion", "Primer producto insertado en Probador.java");
		form1.param("precio", "5");
		Response respuestaFormulario1 = servicio.path("rest").path("catalogo").request().
				post(Entity.entity(form1,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario 1: " + respuestaFormulario1.getStatus());
		
		System.out.println("Formulario para insertar el segundo producto");
		Form form2 = new Form();
		form2.param("id", "2");
		form2.param("nombre", "Silla");
		form2.param("descripcion", "Segundo producto insertado en Probador.java");
		form2.param("precio", "10");
		Response respuestaFormulario2 = servicio.path("rest").path("catalogo").request().
				post(Entity.entity(form2,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario 2: " + respuestaFormulario2.getStatus());
		
		System.out.println("Formulario para insertar el tercer producto");
		Form form3 = new Form();
		form3.param("id", "3");
		form3.param("nombre", "Mesa");
		form3.param("descripcion", "Tercer producto insertado en Probador.java");
		form3.param("precio", "50");
		Response respuestaFormulario3 = servicio.path("rest").path("catalogo").request().
				post(Entity.entity(form3,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario 3: " + respuestaFormulario3.getStatus());
		System.out.println("Formulario para insertar el cuarto producto");
		Form form4 = new Form();
		form4.param("id", "4");
		form4.param("nombre", "Movil");
		form4.param("descripcion", "Cuarto producto insertado en Probador.java");
		form4.param("precio", "700");
		Response respuestaFormulario4 = servicio.path("rest").path("catalogo").request().
				post(Entity.entity(form4,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario 4: " + respuestaFormulario4.getStatus());
		
		// OBTENGO EL CATALOGO DE PRODUCTOS
		System.out.println("\nCATALOGO DE PRODUCTOS:");
		System.out.println(servicio.path("rest").path("catalogo").
		request().accept(MediaType.TEXT_XML).get(String.class));
		
		// ELIMINO EL CUARTO PRODUCTO
		System.out.println("\nFORMULARIO PARA ELIMINAR EL CUARTO PRODUCTO");
		Form form5 = new Form();
		form5.param("id", "4");
		form5.param("nombre", "Movil");
		form5.param("descripcion", "Cuarto producto insertado en Probador.java");
		form5.param("precio", "700");
		Response respuestaFormulario5 = servicio.path("rest").path("catalogo").path("eliminar").path("4").request().
				post(Entity.entity(form5,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario para eliminar el producto 4: " + respuestaFormulario5.getStatus());
		
		// OBTENGO EL CATALOGO DE PRODUCTOS
		System.out.println("\nCATALOGO DE PRODUCTOS SIN EL PRODUCTO 4:");
		System.out.println(servicio.path("rest").path("catalogo").
		request().accept(MediaType.TEXT_XML).get(String.class));
				
		// EL CLIENTE INSERTA PRODUCTOS EN EL CARRITO
		System.out.println("\nFORMULARIO PARA INSERTAR PRODUCTOS EN EL CARRITO");
		System.out.println("Inserto 3 productos con id 1 en el carrito");
		Form form6 = new Form();
		form6.param("id", "1");
		form6.param("cantidad", "3");
		Response respuestaFormulario6 = servicio.path("rest").path("catalogo").path("carrito").request().
				post(Entity.entity(form6,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario para insertar producto 1 en el carrito: " + respuestaFormulario6.getStatus());
		
		System.out.println("Inserto 1 productos con id 3 en el carrito");
		Form form7 = new Form();
		form7.param("id", "3");
		form7.param("cantidad", "1");
		Response respuestaFormulario7 = servicio.path("rest").path("catalogo").path("carrito").request().
				post(Entity.entity(form7,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario para insertar producto 3 en el carrito: " + respuestaFormulario7.getStatus());
		
		// OBTENGO EL CATALOGO DE PRODUCTOS
		System.out.println("\nPRODUCTOS QUE HAY EN EL CARRITO:");
		System.out.println(servicio.path("rest").path("catalogo").path("carrito").
		request().accept(MediaType.TEXT_XML).get(String.class));
		
		// ELIMINO PRODUCTO DEL CARRITO
		System.out.println("\nELIMINO EL PRODUCTO 3 DEL CARRITO");
		Form form8 = new Form();
		form8.param("id", "3");
		Response respuestaFormulario8 = servicio.path("rest").path("catalogo").path("carrito").path("eliminar").path("3").request().
				post(Entity.entity(form8,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
		System.out.println("Respuesta del formulario para eliminar el producto 3 del carrito: " + respuestaFormulario8.getStatus());
		
		// OBTENGO EL CATALOGO DE PRODUCTOS
		System.out.println("\nPRODUCTOS QUE HAY EN EL CARRITO DESPUÉS DE ELIMINAR EL PRODUCTO 3:");
		System.out.println(servicio.path("rest").path("catalogo").path("carrito").
		request().accept(MediaType.TEXT_XML).get(String.class));
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/CastilloNievasJuanManuel").build();
	}
}
