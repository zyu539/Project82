package PROJECT82.dataProvision.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PROJECT82.dataProvision.domain.GridPosition;
import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;

public class MapGriding {

	public static double left = 1000;
	public static double right;
	public static double top;
	public static double bottom = 1000;
	private static Map<String, GridPosition> gps = new HashMap<String, GridPosition>();

	private static void findRange(List<RawPosition> positions) {
		for (RawPosition p : positions) {
			double lat = p.getLatitude();
			double lon = p.getLongtitude();
			if (lat > right) {
				right = lat;
			}
	
			if (lat < left) {
				left = lat;
			}
	
			if (lon > top) {
				top = lon;
			}
	
			if (lon < bottom) {
				bottom = lon;
			}
		}
	}
	
	public static List<Route> makeRoutes(List<RawPosition> positions) {
		List<Route> list = new ArrayList<Route>();
		int prev = 0;
		Route r = null;
		for (RawPosition rp : positions) {
			findRange(positions);
			if (rp.getOnService() == 1 && prev == 0) {
				r = new Route();
			} else if (rp.getOnService() == 1) {
				List<RawPosition> tmp = r.getPositions();
				tmp.add(rp);
				r.setPositions(tmp);
			} else if (rp.getOnService() == 0 && prev == 1) {
				list.add(r);
			}
			prev = rp.getOnService();
		}
		makeGrids(list, 1000, 1000);
		return list;
	}

	private static List<Route> makeGrids(List<Route> routes, int gridNumberX, int gridNumberY) {
		double gridSizeX = (right - left) / gridNumberX;
		double gridSizeY = (top - bottom) / gridNumberY;
		System.out.println(right + "======" + left);
		for (Route r : routes) {
			List<RawPosition> ps = r.getPositions();
			Integer[] prev = new Integer[] {-1, -1};
			for (RawPosition p : ps) {
				int indexX = (int) Math.floor((p.getLatitude() - left) / gridSizeX);
				int indexY = (int) Math.floor((p.getLongtitude() - bottom) / gridSizeY);
				Integer[] tmp = new Integer[] { indexX, indexY };
				if (prev[0] > 0) {
					while (prev[0] < tmp[0]-1 && prev[1] < tmp[1]-1) {
						prev[0] += 1;
						prev[1] += 1;
						r = addGridPositionTORoute(prev, r);
					}
					
					while (prev[0] < tmp[0]-1) {
						prev[0] += 1;
						r = addGridPositionTORoute(prev, r);
					}
					
					while (prev[1] < tmp[1]-1) {
						prev[1] += 1;
						r = addGridPositionTORoute(prev, r);
					}
					
				}
				r = addGridPositionTORoute(tmp, r);
				prev = tmp;
			}
		}

		return routes;
	}
	
	private static Route addGridPositionTORoute(Integer[] tmp, Route r) {
		GridPosition gp = null;
		if (gps.containsKey(tmp[0]+"+"+tmp[1])) {
			gp = gps.get(tmp[0]+"+"+tmp[1]);
			gp.getRoutes().add(r);
			gps.put(tmp[0]+"+"+tmp[1], gp);
		} else {
			gp = new GridPosition(tmp[0], tmp[1]);
			gp.getRoutes().add(r);
			gps.put(tmp[0]+"+"+tmp[1], gp);
		}
		List<GridPosition> grids = r.getGrids();
		grids.add(gp);
		r.setGrids(grids);
		
		return r;
	}
}
