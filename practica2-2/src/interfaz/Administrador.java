package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import modelo.BaseDatos;
import modelo.Producto;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextPane;

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
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;

public class Administrador {

	private JFrame frame;
	private JTable table;
	private JTextField id;
	private JTextField nombre;
	private JTextField descripcion;
	private JTextField precio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Administrador window = new Administrador();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Administrador() {
		initialize();
	}
	
	public void setTable(JTable t) {
		this.table = t;
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 846, 388);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnEliminar_1 = new JButton("Eliminar");
		btnEliminar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println(table.getSelectedRow());
				int id = (int)table.getValueAt(table.getSelectedRow(), 0);
				
				ClientConfig config = new ClientConfig();
				Client cliente = ClientBuilder.newClient(config);
				WebTarget servicio = cliente.target(getBaseURI());
				// ELIMINO EL CUARTO PRODUCTO
				System.out.println("Eliminando producto...");
				Form form5 = new Form();
				form5.param("id", String.valueOf(id));
				Response respuestaFormulario5 = servicio.path("rest").path("catalogo").path("eliminar").path(String.valueOf(id)).request().
						post(Entity.entity(form5,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario para eliminar el producto: " + respuestaFormulario5.getStatus());

				refreshTable();
			}
		});
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Producto p = new Producto();
				int id = (int)table.getValueAt(table.getSelectedRow(), 0);
				String nombre = (String)table.getValueAt(table.getSelectedRow(), 1);
				String descripcion = (String)table.getValueAt(table.getSelectedRow(), 2);
				double precio = (Double)table.getValueAt(table.getSelectedRow(), 3);
				
				ClientConfig config = new ClientConfig();
				Client cliente = ClientBuilder.newClient(config);
				WebTarget servicio = cliente.target(getBaseURI());
				
				System.out.println("Modificando producto...");
				Form form1 = new Form();
				form1.param("id", String.valueOf(id));
				form1.param("nombre", nombre);
				form1.param("descripcion", descripcion);
				form1.param("precio", String.valueOf(precio));
				Response respuestaFormulario1 = servicio.path("rest").path("catalogo").request().
						post(Entity.entity(form1,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario al modificar producto: " + respuestaFormulario1.getStatus());

				refreshTable();
			}
		});
		
		id = new JTextField();
		id.setColumns(10);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		
		descripcion = new JTextField();
		descripcion.setColumns(10);
		
		precio = new JTextField();
		precio.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		
		JLabel lblNombre = new JLabel("Nombre");
		
		JLabel lblDescripcin = new JLabel("Descripción");
		
		JLabel lblPrecio = new JLabel("Precio");
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Producto p = new Producto();
				
				ClientConfig config = new ClientConfig();
				Client cliente = ClientBuilder.newClient(config);
				WebTarget servicio = cliente.target(getBaseURI());
				
				System.out.println("Insertando producto...");
				Form form1 = new Form();
				form1.param("id", id.getText());
				form1.param("nombre", nombre.getText());
				form1.param("descripcion", descripcion.getText());
				form1.param("precio", precio.getText());
				Response respuestaFormulario1 = servicio.path("rest").path("catalogo").request().
						post(Entity.entity(form1,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario al insertar producto: " + respuestaFormulario1.getStatus());
//				p.setId(Integer.parseInt(id.getText()));
//				p.setNombre(nombre.getText());
//				p.setDescripcion(descripcion.getText());
//				p.setPrecio(Double.parseDouble(precio.getText()));
//				
//				BaseDatos.insertar(p);
				refreshTable();
			}
		});
		
		JLabel lblHazClickEn = new JLabel("Haz click en un producto de la tabla y pulsa \"Eliminar\" o modifica alguno de sus datos y pulsa \"Actualizar\"");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNombre)
						.addComponent(lblId)
						.addComponent(lblDescripcin)
						.addComponent(lblPrecio))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(descripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(precio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(btnInsertar)))
					.addGap(111))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(91)
					.addComponent(btnEliminar_1)
					.addGap(43)
					.addComponent(btnActualizar)
					.addContainerGap(517, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHazClickEn)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblId)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNombre)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDescripcin)
								.addComponent(descripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPrecio)
								.addComponent(precio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnInsertar)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnActualizar)
						.addComponent(btnEliminar_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHazClickEn)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		
		DefaultTableModel modelo = new DefaultTableModel();
		JTable table = new JTable(modelo);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Descripci\u00F3n", "Precio"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		int numCols = table.getModel().getColumnCount();
		
		Object [] fila = new Object[numCols];
		
		ClientConfig config = new ClientConfig();
		Client cliente = ClientBuilder.newClient(config);
		WebTarget servicio = cliente.target(getBaseURI());
		// OBTENGO EL CATALOGO DE PRODUCTOS
		System.out.println("Obteniendo catalogo...");
		String responseXML = servicio.path("rest").path("catalogo").
		request().accept(MediaType.TEXT_XML).get(String.class);
		
		List<Producto> productos = new ArrayList<>();
		
		try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(responseXML));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("producto");

	        // iterate the productos
	        for (int i = 0; i < nodes.getLength(); i++) {
	           Element element = (Element) nodes.item(i);
	           Producto pr = new Producto();
	           
	           NodeList idXML = element.getElementsByTagName("id");
	           Element line = (Element) idXML.item(0);
	           pr.setId(Integer.parseInt(getCharacterDataFromElement(line)));
	           
	           NodeList nombreXML = element.getElementsByTagName("nombre");
	           line = (Element) nombreXML.item(0);
	           pr.setNombre(getCharacterDataFromElement(line));
	           
	           NodeList descripcionXML = element.getElementsByTagName("descripcion");
	           line = (Element) descripcionXML.item(0);
	           pr.setDescripcion(getCharacterDataFromElement(line));
	           
	           NodeList precioXML = element.getElementsByTagName("precio");
	           line = (Element) precioXML.item(0);
	           pr.setPrecio(Double.parseDouble(getCharacterDataFromElement(line)));
	           
	           productos.add(pr);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		for (Producto p: productos) {
			fila[0] = p.getId();
			fila[1] = p.getNombre();
			fila[2] = p.getDescripcion();
			fila[3] = p.getPrecio();
			((DefaultTableModel) table.getModel()).addRow(fila);
		}
		
		setTable(table);
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void refreshTable() {
		this.frame.dispose();
		Administrador window = new Administrador();
		window.frame.setVisible(true);
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/CastilloNievasJuanManuel").build();
	}
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "";
	  }
	
}
