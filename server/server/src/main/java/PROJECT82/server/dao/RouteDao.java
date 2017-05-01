package PROJECT82.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;

import PROJECT82.server.domain.Route;
import PROJECT82.server.domain.PersistenceManager;

public class RouteDao {
	
	public Response postRouteData(Route route) {
		EntityManager em = PersistenceManager.instance()
				.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(route);
		tx.commit();
		return Response.ok().build();		
	}	
}
