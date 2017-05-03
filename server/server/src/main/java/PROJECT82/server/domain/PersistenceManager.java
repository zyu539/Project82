package PROJECT82.server.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	private static PersistenceManager instance = null;
	
	private EntityManagerFactory emf;
	
	protected PersistenceManager() {
		emf = Persistence.createEntityManagerFactory("server");
	}
	
	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
	public static PersistenceManager instance() {
		if (instance == null) {
			instance = new PersistenceManager();
		}
		return instance;
	}
}

