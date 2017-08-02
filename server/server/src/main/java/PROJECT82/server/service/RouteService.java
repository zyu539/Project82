package PROJECT82.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import PROJECT82.server.dao.RouteDao;
import PROJECT82.server.domain.GridPosition;
import PROJECT82.server.domain.Route;
import PROJECT82.server.util.Algorithms;
import PROJECT82.server.util.MapGriding;

@Path("/route")
public class RouteService {

	@Inject
	private RouteDao routeDao;
	private final int period = 3000;
	private final double gridSizeX = 0;
	private final double gridSizeY = 0;
	private final double left = 0;
	private final double bottom = 0;

	@POST
	@Path("/data")
	@Consumes({ MediaType.APPLICATION_XML })
	public double postRouteData(Route route) {
		System.out.println("receive!!!!");
		System.out.println("id: " + route.getId());
		List<GridPosition> lg = new ArrayList<GridPosition>();
		List<Integer[]>list = MapGriding.makeGrids(route, gridSizeX, gridSizeY, left, bottom);
		Integer[] start = list.get(0);
		Integer[] end = list.get(list.size() - 1);
		List<Route> routes = routeDao.retrieveOrderedData(start, end);
		for (Integer[] p : list) {
			lg.add(routeDao.retrieveGrids(p[0], p[1]));
		}		
		route.setGrids(lg);
		Algorithms alg = new Algorithms(1, 1);		
		return alg.IBatAlg(routes, route);
	}

	@GET
	@Path("/period")
	public Response postPeriod() {
		System.out.println();
		return Response.ok(period).build();
	}

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();

	}
}
