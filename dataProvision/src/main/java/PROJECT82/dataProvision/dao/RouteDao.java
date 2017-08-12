package PROJECT82.dataProvision.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import PROJECT82.dataProvision.domain.Forest;
import PROJECT82.dataProvision.domain.GridPosition;
import PROJECT82.dataProvision.domain.PersistenceManager;
import PROJECT82.dataProvision.domain.Route;

public class RouteDao {
	public List<Route> retrieveOrderedData(int[] start, int[] end) {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		String sql1 = "select * from GridPosition where indexX = " + start[0] + " and indexY = " + start[1];
		String sql2 = "select * from GridPosition where indexX = " + end[0] + " and indexY = " + end[1];
		tx.begin();
		Query query = em.createNativeQuery(sql1, GridPosition.class);
		GridPosition startP = (GridPosition)query.getSingleResult();
		query = em.createNativeQuery(sql2, GridPosition.class);
		GridPosition endP = (GridPosition)query.getSingleResult();
		List<Route> passStart = startP.getRoutes();
		List<Route> passEnd = endP.getRoutes();
		List<Route> results = new ArrayList<Route>();
		for (Route r : passStart) {
			if (passEnd.contains(r)) {
				List<GridPosition> tmp = r.getGrids();
				if (tmp.indexOf(startP) <= tmp.indexOf(endP)) {
					results.add(r);
				}
			}
		}
		tx.commit();
		em.close();
		return results;
	}
	
	public List<Route> retrieveRoutes() {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		String sql1 = "select * from Route";
		tx.begin();
		Query query = em.createNativeQuery(sql1, Route.class);
		@SuppressWarnings("unchecked")
		List<Route> routes = query.getResultList();
		tx.commit();
		em.close();
		return routes;
	}
	
	public void persistForest(Forest f) {
		EntityManager em = PersistenceManager.instance().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(f);
		tx.commit();
		em.close();
	}
}
