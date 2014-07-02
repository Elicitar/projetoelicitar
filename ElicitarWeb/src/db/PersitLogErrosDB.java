package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Tblogger;

public class PersitLogErrosDB {

	private static final String PERSISTENCE_UNIT_NAME = "ElicitarWeb";
	private static EntityManagerFactory factory;
	private static PersitLogErrosDB vInstance;
	EntityManager em;

	public PersitLogErrosDB() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static PersitLogErrosDB getInstance() {
		if (vInstance == null) {
			vInstance = new PersitLogErrosDB();
		}
		return vInstance;
	}

	public void addLogsToDB(Tblogger logger) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(logger);
		em.getTransaction().commit();
		em.close();
	}

}
