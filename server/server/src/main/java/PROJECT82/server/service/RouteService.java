package PROJECT82.server.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import PROJECT82.server.dao.RouteDao;
import PROJECT82.server.domain.Position;
import PROJECT82.server.domain.Route;
import PROJECT82.server.util.RouteUtil;

@Path("/route")
public class RouteService {
	
	@Inject
    private RouteDao routeDao;
	
	@POST
	@Path("/data")
	@Consumes("application/xml")
	public Response postRouteData(Route route) {
		Set<Position> tmp = new HashSet<Position>();
		Set<Position> positions = route.getRoute();
		Position prev = null;
		for (Position p : positions) {
			if (prev == null) {
				p.setSpeed(0);
				tmp.add(p);
			} else {
				p.setSpeed(RouteUtil.distFrom(prev.getLatitude(), prev.getLongtitude(), p.getLatitude(), p.getLongtitude()));
				tmp.add(p);
			}
			prev = p;
		}
		route.setRoute(tmp);
		
		return routeDao.postRouteData(route);
	}
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
}
