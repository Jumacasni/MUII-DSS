package recurso;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modelo.*;

// Hara corresponder el recurso al URL catalogo

@Path("/catalogo")
public class CatalogoRecurso {
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	@GET
	@Produces({MediaType.TEXT_XML} )
	public List<Producto> getProductos() {
		List<Producto> productos = BaseDatos.getProductos();
		return productos;
	}
		
	// Para obtener el numero total de elementos en el proveedor de contenidos
	@GET
	@Path("cont")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int cont = BaseDatos.getProductos().size();
		return String.valueOf(cont);
	}
	
//	//Para enviar datos al servidor como un formulario Web
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newProducto(@FormParam("id") String id, @FormParam("nombre") String nombre, @FormParam("descripcion") String descripcion,
	@FormParam("precio") String precio, @Context HttpServletResponse servletResponse) throws IOException {
		Producto producto = new Producto(Integer.parseInt(id), nombre, Double.parseDouble(precio));
		if (descripcion != null) {
			producto.setDescripcion(descripcion);
		}
		
		if(BaseDatos.existeId(Integer.parseInt(id))) {
			BaseDatos.actualizar(producto);
		}
		else {
			BaseDatos.insertar(producto);
		}
		
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo");
	}
	
	 @POST
	 @Path("/eliminar/{producto}")
	 @Produces(MediaType.TEXT_HTML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void deleteProducto(@PathParam("producto") String id,  @Context HttpServletResponse servletResponse) throws IOException{
		Producto producto = new Producto();
		producto.setId(Integer.parseInt(id));
		
		BaseDatos.eliminar(producto);
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo");
	}
	 
	 @GET
	 @Produces({ MediaType.TEXT_XML })
	 @Path("/{producto}")
	 public Producto getCatalogo(@PathParam("producto") String id) {
		
		return BaseDatos.getProducto(Integer.parseInt(id));
	}
	
	// Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	@GET
	@Produces({MediaType.TEXT_XML} )
	@Path("carrito")
	public List<Carrito> getProductosCarrito() {
		List<Carrito> productosCarrito = BaseDatosCarrito.getCarrito();
		return productosCarrito;
	}
//		//Para enviar datos al servidor como un formulario Web
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("carrito")
	public void newProductoCarrito(@FormParam("id") String id, @FormParam("cantidad") String cantidad,
	@Context HttpServletResponse servletResponse) throws IOException {
		Producto producto = BaseDatos.getProducto(Integer.parseInt(id));
		if(BaseDatosCarrito.existeId(Integer.parseInt(id))) {
			BaseDatosCarrito.actualizar(producto, Integer.parseInt(cantidad));
		}
		else {
			BaseDatosCarrito.insertar(producto, Integer.parseInt(cantidad));
		}
	}
	
	@POST
	 @Path("/carrito/eliminar/{producto}")
	 @Produces(MediaType.TEXT_HTML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void deleteProductoCarrito(@PathParam("producto") String id,  @Context HttpServletResponse servletResponse) throws IOException{
		Producto producto = new Producto();
		producto.setId(Integer.parseInt(id));
		
		BaseDatosCarrito.eliminar(producto);
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo/carrito");
	}
	
	@POST
	 @Path("/carrito/{producto}")
	 @Produces(MediaType.TEXT_HTML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void aniadirProductoCarrito(@PathParam("producto") String id, @FormParam("cantidad") String cantidad, @Context HttpServletResponse servletResponse) throws IOException{
		Producto producto = new Producto();
		producto.setId(Integer.parseInt(id));
		
		if(cantidad.isEmpty()) {
			cantidad = "1";
		}
		
		BaseDatosCarrito.insertar(producto, Integer.parseInt(cantidad));
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel");
	}
}
