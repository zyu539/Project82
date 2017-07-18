package PROJECT82.dataProvision.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import PROJECT82.dataProvision.domain.PersistenceManager;
import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;

public class SampleDao {
	
	public void persistData(List<RawPosition> plist) {
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		System.out.println("??????");
		tx.begin();
		for (RawPosition p : plist) {
		    em.persist(p);
		}
		tx.commit();
		System.out.println("lol-ashe");
		em.close();
	}
	
	public List<RawPosition> retrieveOrderedData() {
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		String sql = "select * from RawPosition order by taxiId, date";
		tx.begin();
		Query query = em.createNativeQuery(sql, RawPosition.class);
		
		@SuppressWarnings("unchecked")
		List<RawPosition> results = query.getResultList();
		tx.commit();
		System.out.println("lol-ahri");
		em.close();
		return results;
	}
	
	public void persistRoute(List<Route> routes) {
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		for (Route r : routes) {
		    em.persist(r);
		}
		tx.commit();
		System.out.println("lol-catlyn");
		em.close();
	}
}
