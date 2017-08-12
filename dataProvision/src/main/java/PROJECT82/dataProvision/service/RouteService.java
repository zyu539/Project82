package PROJECT82.dataProvision.service;

import java.util.List;

import PROJECT82.dataProvision.dao.RouteDao;
import PROJECT82.dataProvision.domain.Forest;
import PROJECT82.dataProvision.domain.Route;

public class RouteService {
	
	RouteDao rd = new RouteDao();
	
	public List<Route> retrieveRoute(int[] start, int[] end) {
		return rd.retrieveOrderedData(start, end);
	}
	
	public List<Route> retrieveRoute() {
		return rd.retrieveRoutes();
	}
	
	public void persistForest(Forest f) {
		rd.persistForest(f);
	}

}
