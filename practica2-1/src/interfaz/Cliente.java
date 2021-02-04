package interfaz;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import modelo.BaseDatos;
import modelo.BaseDatosCarrito;
import modelo.Carrito;
import modelo.Producto;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import org.w3c.dom.CharacterData;

import java.io.*;

public class Cliente {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTextField cantidad;
	private JTextField total;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
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
	public Cliente() {
		initialize();
	}
	
	public void setTable(JTable t) {
		this.table = t;
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public void setTable_1(JTable t) {
		this.table_1 = t;
	}
	
	public JTable getTable_1() {
		return this.table_1;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1152, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		total = new JTextField();
		total.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
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
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		DefaultTableModel modelo_1 = new DefaultTableModel();
		JTable table_1 = new JTable(modelo);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Producto", "Nombre", "Cantidad", "Total"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		numCols = table_1.getModel().getColumnCount();
		
		fila = new Object[numCols];

		// OBTENGO EL CARRITO
		System.out.println("Obteniendo carrito...");
		responseXML = servicio.path("rest").path("catalogo").path("carrito").
		request().accept(MediaType.TEXT_XML).get(String.class);
		
		List<Carrito> carrito = new ArrayList<>();
		
		try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(responseXML));

	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("carrito");

	        // iterate the carrito
	        for (int i = 0; i < nodes.getLength(); i++) {
	           Element element = (Element) nodes.item(i);
	           Carrito ca = new Carrito();
	           
	           NodeList cantidadXML = element.getElementsByTagName("cantidad");
	           Element line = (Element) cantidadXML.item(0);
	           ca.setCantidad(Integer.parseInt(getCharacterDataFromElement(line)));
	           
	           NodeList idXML = element.getElementsByTagName("id");
	           line = (Element) idXML.item(0);
	           ca.setId(Integer.parseInt(getCharacterDataFromElement(line)));
	           
	           carrito.add(ca);
	        }
	        
	        nodes = doc.getElementsByTagName("producto");

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
	           
	           carrito.get(i).setProducto(pr);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		double suma = 0;
		for (Carrito c: carrito) {
			fila[0] = c.getId();
			fila[1] = c.getProducto().getNombre();
			fila[2] = c.getCantidad();
			fila[3] = c.getCantidad()*c.getProducto().getPrecio();
			suma = suma + c.getCantidad()*c.getProducto().getPrecio();
			((DefaultTableModel) table_1.getModel()).addRow(fila);
		}
		
		total.setText(Double.toString(suma));
		setTable_1(table_1);
		scrollPane_1.setViewportView(table_1);
		
		JButton btnNewButton = new JButton("Añadir al carrito");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Producto p = new Producto();
				int id = (int)table.getValueAt(table.getSelectedRow(), 0);
				
				System.out.println("Insertando producto en el carrito...");
				Form form7 = new Form();
				form7.param("id", String.valueOf(id));
				String c = cantidad.getText();
				if(c.isEmpty()) {
					c = "1";
				}
				form7.param("cantidad", c);
				Response respuestaFormulario7 = servicio.path("rest").path("catalogo").path("carrito").request().
						post(Entity.entity(form7,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario para insertar producto en el carrito: " + respuestaFormulario7.getStatus());

				refreshTable();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblCantidad = new JLabel("Cantidad");
		
		cantidad = new JTextField();
		cantidad.setColumns(10);
		
		JButton btnModificarCantidad = new JButton("Modificar cantidad");
		btnModificarCantidad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = (int)table_1.getValueAt(table_1.getSelectedRow(), 0);
				int cantidad = (int)table_1.getValueAt(table_1.getSelectedRow(), 2);
				
				System.out.println("Modificando cantidad en el carrito...");
				Form form7 = new Form();
				form7.param("id", String.valueOf(id));
				form7.param("cantidad", String.valueOf(cantidad));
				Response respuestaFormulario7 = servicio.path("rest").path("catalogo").path("carrito").request().
						post(Entity.entity(form7,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario para modificar cantidad del producto en el carrito: " + respuestaFormulario7.getStatus());

				refreshTable();
			}
		});
		
		JButton btnEliminarProductoDel = new JButton("Eliminar producto del carrito");
		btnEliminarProductoDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = (int)table_1.getValueAt(table_1.getSelectedRow(), 0);
				// ELIMINO PRODUCTO DEL CARRITO
				System.out.println("Eliminando producto del carrito...");
				Form form8 = new Form();
				form8.param("id", String.valueOf(id));
				Response respuestaFormulario8 = servicio.path("rest").path("catalogo").path("carrito").path("eliminar").path(String.valueOf(id)).request().
						post(Entity.entity(form8,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
				System.out.println("Respuesta del formulario para eliminar el producto del carrito: " + respuestaFormulario8.getStatus());

				refreshTable();
			}
		});
		
		JLabel lblInstruccionesDeUso = new JLabel("Instrucciones de uso:");
		
		JTextPane txtpnSeleccionarUnProducto = new JTextPane();
		txtpnSeleccionarUnProducto.setText("1. Seleccionar un producto, introducir cantidad y pulsar \"Añadir al carrito\"");
		
		JTextPane txtpnParaEliminar = new JTextPane();
		txtpnParaEliminar.setText("2. Para eliminar un producto del carrito, seleccionar producto en el carrito y pulsar \"Eliminar\"");
		
		JTextPane txtpnParaModificar = new JTextPane();
		txtpnParaModificar.setText("3. Para modificar una cantidad, cambiar cantidad en la tabla del carrito y pulsar \"Modificar cantidad\"");
		
		JTextPane txtpnTambinSe = new JTextPane();
		txtpnTambinSe.setText("4. También se puede modificar la cantidad seleccionando el producto del catálogo, poniendo la cantidad y pulsando \"Añadir al carrito\"");
		
		JLabel lblCatlogo = new JLabel("Catálogo");
		
		JLabel lblCarrito = new JLabel("Carrito");
		
		JLabel lblAPagar = new JLabel("A pagar:");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblInstruccionesDeUso)
										.addComponent(txtpnSeleccionarUnProducto, 0, 0, Short.MAX_VALUE)
										.addComponent(txtpnParaEliminar, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtpnParaModificar, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(txtpnTambinSe, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(176)
							.addComponent(lblCatlogo)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(48)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(34)
											.addComponent(lblCantidad))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(14)
											.addComponent(cantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnNewButton)))
									.addGap(26)
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
									.addGap(19))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblAPagar)
											.addGap(32)
											.addComponent(total, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnEliminarProductoDel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnModificarCantidad)))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblCarrito)
							.addGap(202))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(lblCatlogo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(17)
									.addComponent(lblCantidad)
									.addGap(7)
									.addComponent(cantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(btnNewButton))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCarrito)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)))
					.addGap(1)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnModificarCantidad)
								.addComponent(btnEliminarProductoDel)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblInstruccionesDeUso)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtpnSeleccionarUnProducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtpnParaEliminar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtpnParaModificar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtpnTambinSe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(total, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAPagar))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void refreshTable() {
		this.frame.dispose();
		Cliente window = new Cliente();
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
