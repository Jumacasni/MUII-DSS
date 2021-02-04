package modelo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class BaseDatosCarrito {
	private static final String PERSISTENCE_UNIT_NAME = "practica2";
	private static EntityManagerFactory factory;
	
	// Inserta un producto en el carrito con la cantidad
	public static void insertar(Producto p, int c) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		// Si el producto no existe
		if (!existeId(p.getId())) {
			em.getTransaction().begin();
			Carrito carrito = new Carrito();
			carrito.setId(p.getId());
			carrito.setProducto(p);
			carrito.setCantidad(c);
			em.persist(carrito);
			em.getTransaction().commit();
			em.close();
		}
		
		else {
			actualizar(p, c);
		}
	}

	// Actualiza la cantidad de un producto
	public static void actualizar(Producto p, int cantidad) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(p.getId())) {
			Query q = em.createQuery("SELECT c FROM Carrito c WHERE c.id = :id");
			q.setParameter("id", p.getId());
			
			Carrito c = (Carrito) q.getSingleResult();
			c.setCantidad(cantidad);
			
			em.getTransaction().begin();
			em.merge(c);
			em.getTransaction().commit();
			em.close();
		}
	}

	// Borrar un producto del carrito
	public static void eliminar(Producto p) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		if (existeId(p.getId())) {
			Query q = em.createQuery("SELECT c FROM Carrito c WHERE c.id = :id");
			q.setParameter("id", p.getId());

			Carrito c = (Carrito) q.getSingleResult();
			
			em.getTransaction().begin();
			em.remove(c);
			em.getTransaction().commit();			
			em.close();
		}
	}

	// Si el producto está en el carrito
	public static boolean existeId(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT c FROM Carrito c WHERE c.id = :id");
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
	
	// Devuelve los productos del carrito
	public static List<Carrito> getCarrito() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		Query q = em.createQuery("SELECT c FROM Carrito c");
		
		List<Carrito> resultado = (List<Carrito>)q.getResultList();
		em.close();
		
		return resultado;
	}
}
