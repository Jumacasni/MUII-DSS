package modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BaseDatos {
	private static final String PERSISTENCE_UNIT_NAME = "practica2";
	private static EntityManagerFactory factory;
	
	// Inserta un producto
	public static void insertar(Producto p) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		// Si el producto no existe
		if (!existeId(p.getId())) {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
			em.close();
		}
	}

	// Actualiza un producto existente
	public static void actualizar(Producto p) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(p.getId())) {
			Query q = em.createQuery("SELECT p FROM Producto p WHERE p.id = :id");
			q.setParameter("id", p.getId());
			
			Producto producto = (Producto) q.getSingleResult();
			producto.setNombre(p.getNombre());
			producto.setDescripcion(p.getDescripcion());
			producto.setPrecio(p.getPrecio());
			
			em.getTransaction().begin();
			em.merge(producto);
			em.getTransaction().commit();
			em.close();
		}
	}

	// Borrar un producto
	public static void eliminar(Producto p) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(p.getId())) {
			Query q = em.createQuery("SELECT p FROM Producto p WHERE p.id = :id");
			q.setParameter("id", p.getId());

			Producto producto = (Producto) q.getSingleResult();
			
			em.getTransaction().begin();
			em.remove(producto);
			em.getTransaction().commit();			
			em.close();
		}
	}

	// Selecciona un producto
	public static Producto getProducto(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Producto p = null;

		if (existeId(id)) {
			Query q = em.createQuery("SELECT p FROM Producto p WHERE p.id = :id");
			q.setParameter("id", id);
			p = (Producto) q.getSingleResult();
			em.close();
		}

		return p;
	}

	// Si el producto existe
	public static boolean existeId(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT p FROM Producto p WHERE p.id = :id");
		q.setParameter("id", id);
		
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} finally {
			em.close();
		}
	}
	
	// Devuelve una lista de productos
	public static List<Producto> getProductos() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT p FROM Producto p");
		
		List<Producto> resultado = (List<Producto>)q.getResultList();
		em.close();
		
		return resultado;
	}
}
