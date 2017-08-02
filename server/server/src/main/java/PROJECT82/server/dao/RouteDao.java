package PROJECT82.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

import PROJECT82.server.domain.Route;
import PROJECT82.server.domain.GridPosition;
import PROJECT82.server.domain.PersistenceManager;

public class RouteDao {
	
	public Response postRouteData(Route route) {
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(route);
		tx.commit();
		em.close(); 
		return Response.ok().build();		
	}
	
	public GridPosition retrieveGrids(int x, int y) {
		String sql = "select * from GridPosition where indexX = " + x + " and indexY = " + y;
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNativeQuery(sql, GridPosition.class);
		GridPosition gp = (GridPosition) query.getSingleResult();
		tx.commit();
		em.close();
		return gp;
	}
	
	public List<Route> retrieveOrderedData(Integer[] start, Integer[] end) {
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
}
