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

import templateHTML.Template;

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
	@Produces({MediaType.TEXT_HTML} )
	public String getProductos() {
		List<Producto> productos = BaseDatos.getProductos();
		Template t = new Template();
		String stringHTML = t.getHTMLTemplate();
		
		for (Producto p : productos) {
			String id = Integer.toString(p.getId());
			String nombre = p.getNombre();
			if (nombre == null)
				nombre = "";
			String descripcion = p.getDescripcion();
			if (descripcion == null) {
				descripcion = "";
			}
			String precio = String.valueOf(p.getPrecio());
			if (precio == null) {
				precio = "";
			}
			
			stringHTML += "<tr>\n";
			stringHTML += "<th scope='row'>"+id+"</th>\n";
			stringHTML += "<th scope='row'>"+nombre+"</th>\n";
			stringHTML += "<th scope='row'>"+descripcion+"</th>\n";
			stringHTML += "<th scope='row'>"+precio+"</th>\n";
			stringHTML += "<th scope='row'><form class=\"well form-horizontal\" action=\"http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo/eliminar/"+Integer.toString(p.getId())+"\" method=\"post\" id=\"contact_form\"><button id=\"button\" type=\"submit\" class=\"btn btn-primary\">Eliminar</button></form></th>\n";
			stringHTML += "</tr>\n";
		}
		
		stringHTML += "</tbody>\n";
		stringHTML += "</table>\n";
		stringHTML += "</body>\n";
		stringHTML += "</html>\n";
		
		return stringHTML;
	}
	
	// Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	@GET
	@Produces({MediaType.TEXT_XML} )
	public List<Producto> getProductosXML() {
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
		
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/");
	}
	
	 @POST
	 @Path("/eliminar/{producto}")
	 @Produces(MediaType.TEXT_HTML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void deleteProducto(@PathParam("producto") String id,  @Context HttpServletResponse servletResponse) throws IOException{
		Producto producto = new Producto();
		producto.setId(Integer.parseInt(id));
		
		BaseDatos.eliminar(producto);
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/");
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
	@Produces({MediaType.TEXT_HTML} )
	@Path("catalogoClientes")
	public String getCatalogoClientes() {
		List<Producto> productos = BaseDatos.getProductos();
		Template t = new Template();
		String stringHTML = t.getHTMLTemplate();
		
		for (Producto p : productos) {
			String id = Integer.toString(p.getId());
			String nombre = p.getNombre();
			if (nombre == null)
				nombre = "";
			String descripcion = p.getDescripcion();
			if (descripcion == null) {
				descripcion = "";
			}
			String precio = String.valueOf(p.getPrecio());
			if (precio == null) {
				precio = "";
			}
			
			stringHTML += "<tr>\n";
			stringHTML += "<th scope='row'>"+id+"</th>\n";
			stringHTML += "<th scope='row'>"+nombre+"</th>\n";
			stringHTML += "<th scope='row'>"+descripcion+"</th>\n";
			stringHTML += "<th scope='row'>"+precio+"</th>\n";
			stringHTML += "<th scope='row'><form class=\"well form-horizontal\" action=\"http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo/carrito/"+Integer.toString(p.getId())+"\" method=\"post\" id=\"contact_form\"><button id=\"button\" type=\"submit\" class=\"btn btn-primary\">Añadir</button><input name=\"cantidad\" placeholder=\"Cantidad\" class=\"form-control\" type=\"text\"></form></th>\n";
			stringHTML += "</tr>\n";
		}
		
		stringHTML += "</tbody>\n";
		stringHTML += "</table>\n";
		stringHTML += "</body>\n";
		stringHTML += "</html>\n";
		
		return stringHTML;
	}
	
	// Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	@GET
	@Produces({MediaType.TEXT_HTML} )
	@Path("carrito")
	public String getProductosCarrito() {
		List<Carrito> productosCarrito = BaseDatosCarrito.getCarrito();
		Template t = new Template();
		String stringHTML = t.getHTMLTemplateCarrito();
		double total = 0;
		
		for (Carrito p : productosCarrito) {
			total = total + p.getProducto().getPrecio()*p.getCantidad();
			String id = Integer.toString(p.getProducto().getId());
			String nombre = p.getProducto().getNombre();
			if (nombre == null)
				nombre = "";
			String cantidad = Integer.toString(p.getCantidad());
			if (cantidad == null) {
				cantidad = "";
			}
			String precio = String.valueOf(p.getProducto().getPrecio()*p.getCantidad());
			if (precio == null) {
				precio = "";
			}
			
			stringHTML += "<tr>\n";
			stringHTML += "<th scope='row'>"+id+"</th>\n";
			stringHTML += "<th scope='row'>"+nombre+"</th>\n";
			stringHTML += "<th scope='row'>"+cantidad+"</th>\n";
			stringHTML += "<th scope='row'>"+precio+"</th>\n";
			stringHTML += "<th scope='row'><form class=\"well form-horizontal\" action=\"http://localhost:8080/CastilloNievasJuanManuel/rest/catalogo/carrito/eliminar/"+Integer.toString(p.getId())+"\" method=\"post\" id=\"contact_form\"><button id=\"button\" type=\"submit\" class=\"btn btn-primary\">Eliminar</button></form></th>\n";
			stringHTML += "</tr>\n";
		}
		
		stringHTML += "<tr>\n";
		stringHTML += "<th scope='row'>TOTAL A PAGAR</th>\n";
		stringHTML += "<th scope='row'></th>\n";
		stringHTML += "<th scope='row'></th>\n";
		stringHTML += "<th scope='row'>"+String.valueOf(total)+"</th>\n";
		stringHTML += "<th scope='row'></th>\n";
		
		stringHTML += "</tbody>\n";
		stringHTML += "</table>\n";
		stringHTML += "</body>\n";
		stringHTML += "</html>\n";
		
		return stringHTML;
	}
	
	// Devolvera la lista de todos lo elementos contenidos en el proveeedor
	//a las aplicaciones cliente
	@GET
	@Produces({MediaType.TEXT_XML} )
	@Path("carrito")
	public List<Carrito> getProductosCarritoXML() {
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
			;
		}
		else {
			BaseDatosCarrito.insertar(producto, Integer.parseInt(cantidad));
		}
		
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/carrito.html");
	}
	
	@POST
	 @Path("/carrito/eliminar/{producto}")
	 @Produces(MediaType.TEXT_HTML)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public void deleteProductoCarrito(@PathParam("producto") String id,  @Context HttpServletResponse servletResponse) throws IOException{
		Producto producto = new Producto();
		producto.setId(Integer.parseInt(id));
		
		BaseDatosCarrito.eliminar(producto);
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/carrito.html");
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
		servletResponse.sendRedirect("http://localhost:8080/CastilloNievasJuanManuel/carrito.html");
	}
}
