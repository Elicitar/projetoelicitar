package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import requirements.RequisitoBase;
import model.Tbrequisito;

public class ReqDB {
	private static final String PERSISTENCE_UNIT_NAME = "ElicitarWeb";
	private static EntityManagerFactory factory;
	private static ReqDB vInstance;
	EntityManager em;

	public ReqDB() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static ReqDB getInstance() {
		if (vInstance == null) {
			vInstance = new ReqDB();
		}
		return vInstance;
	}

	public Tbrequisito getReqByName(String name) {
		Query q = em
				.createQuery("select t from Tbrequisito t where t.nome= :nome");
		q.setParameter("nome", name);
		@SuppressWarnings("unchecked")
		List<Tbrequisito> todoList = q.getResultList();
		if (todoList.size() > 0) {
			return todoList.get(0);
		}
		return null;
	}

	public List<Tbrequisito> getAllReqs() {
		em = factory.createEntityManager();
		Query q = em.createQuery("select t from Tbrequisito t");
		@SuppressWarnings("unchecked")
		List<Tbrequisito> todoList = q.getResultList();
		em.close();
		return todoList;
	}


	
	public void addReqToDB(RequisitoBase req) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Tbrequisito tb = getReqByName(req.getNome());
		if (tb == null) {
			tb = new Tbrequisito();
		}
		tb.setNome(req.getNome());
		tb.setDescricao(req.getFullDesc());
		tb.setTipo(req.getTipoRequisitoStr());
		tb.setObjetivo(req.getObjetivo());
		tb.setMorfname(req.getMorfName());
		tb.setMorfdesc(req.getMorfDesc());
		em.persist(tb);
		em.getTransaction().commit();

		em.close();
	}
	
	public void removeFromDB(RequisitoBase req){
		em = factory.createEntityManager();
		em.getTransaction().begin();
		Tbrequisito tb = getReqByName(req.getNome());
		if (tb != null) {
			em.remove(tb);
		}
		em.getTransaction().commit();
		em.close();
	}
}
