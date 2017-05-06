package PROJECT82.server.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import PROJECT82.server.dao.RouteDao;
import PROJECT82.server.domain.Position;
import PROJECT82.server.domain.Route;
import PROJECT82.server.util.RouteUtil;

@Path("/route")
public class RouteService {

	@Inject
	private RouteDao routeDao;
	private final int period = 3000;

	@POST
	@Path("/data")
	@Consumes({ MediaType.APPLICATION_XML })
	public Response postRouteData(Route route) {
		route.setPeriod(period);
		System.out.println("receive!!!!");
		System.out.println("id: " + route.getId());
		Set<Position> tmp = new HashSet<Position>();
		Set<Position> positions = route.getPositions();
		Position prev = null;
		for (Position p : positions) {
			if (prev == null) {
				p.setSpeed(0);
				tmp.add(p);
			} else {
				p.setSpeed(RouteUtil.calSpeed(prev.getLatitude(), prev.getLongitude(), p.getLatitude(),
						p.getLongitude(), ((double)period)/1000));
				tmp.add(p);
			}
			prev = p;
			System.out.println(p.getSpeed());
		}
		route.setPositions(tmp);

		return routeDao.postRouteData(route);
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
