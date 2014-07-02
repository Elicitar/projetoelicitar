package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Tbcaracteristica;

public class CaracteristicasDB {
	private static final String PERSISTENCE_UNIT_NAME = "ElicitarWeb";
	private static EntityManagerFactory factory;
	private static CaracteristicasDB vInstance;
	EntityManager em;

	public CaracteristicasDB() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static CaracteristicasDB getInstance() {
		if (vInstance == null) {
			vInstance = new CaracteristicasDB();
		}
		return vInstance;
	}

	public List<Tbcaracteristica> getCaraceteristicaByTipo(String tipo) {
		em = factory.createEntityManager();
		Query q = em
				.createQuery("select t from Tbcaracteristica t where t.tipocaracteristica = :tipo");
		q.setParameter("tipo", tipo);
		@SuppressWarnings("unchecked")
		List<Tbcaracteristica> todoList = q.getResultList();
		em.close();
		if (todoList.size() > 0) {
			return todoList;
		}
		return null;
	}

	public List<Tbcaracteristica> getAllCaracteristicas() {
		em = factory.createEntityManager();
		Query q = em.createQuery("select t from Tbcaracteristica t");
		@SuppressWarnings("unchecked")
		List<Tbcaracteristica> todoList = q.getResultList();
		em.close();
		return todoList;
	}


	
	public void addCaracteristicaToDB(String tipoCaracteristica, String caracteristica, String morfCaracteristica) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Tbcaracteristica tb = new Tbcaracteristica();
		tb .setCaracteristica(caracteristica);
		tb.setCaracteristicamorf(morfCaracteristica);
		tb.setTipocaracteristica(tipoCaracteristica);
		em.persist(tb);
		em.getTransaction().commit();

		em.close();
	}
	
	public void removeFromDB(int id){
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Tbcaracteristica tb = getCaraceteristicaById(id);
		if (tb != null) {
			em.remove(tb);
		}
		em.getTransaction().commit();
		em.close();
	}

	private Tbcaracteristica getCaraceteristicaById(int id) {
		em = factory.createEntityManager();
		Query q = em
				.createQuery("select t from Tbcaracteristica t where t.idtbcaracteristica = :id");
		q.setParameter("tipo", id);
		@SuppressWarnings("unchecked")
		List<Tbcaracteristica> todoList = q.getResultList();
		em.close();
		if (todoList.size() > 0) {
			return todoList.get(0);
		}
		return null;
	}
}
