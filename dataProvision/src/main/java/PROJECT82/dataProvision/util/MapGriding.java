package PROJECT82.dataProvision.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import PROJECT82.dataProvision.domain.GridPosition;
import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;

public class MapGriding {

	public static double left = 103.931497;
	public static double right = 104.201452;
	public static double top = 30.773799;
	public static double bottom = 30.571680;
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
		System.out.println("making routes...");
		List<Route> list = new ArrayList<Route>();
		int prev = 0;
		Route r = null;
		//findRange(positions);
		long prevT = 0;
		boolean isOut = false;
		for (RawPosition rp : positions) {
			long time = RouteUtil.StringToSec(rp.getDate());
			if (rp.getOnService() == 1 && isOut(rp.getLatitude(), rp.getLongtitude())) {
				isOut = true;
				//System.out.println("latitude:" + rp.getLatitude());
				//System.out.println("longtitude:" + rp.getLongtitude());
			}
			if (rp.getOnService() == 1 && prev == 0) {
				r = new Route();
				List<RawPosition> tmp = r.getPositions();
				tmp.add(rp);
				r.setPositions(tmp);
				prevT = time;
			} else if (rp.getOnService() == 1 && (time - prevT) > 16000) {
				List<RawPosition> tmp = r.getPositions();
				tmp.add(rp);
				r.setPositions(tmp);
				prevT = time;
			} else if (rp.getOnService() == 0 && prev == 1) {
				prevT = time;
				if (isOut) {
					isOut = false;
					prev = rp.getOnService();
					continue;
				}
				list.add(r);
			}
			prev = rp.getOnService();
		}
		write0a(list);
		System.out.println("making grids....");
		makeGrids(list, 500, 500);
		System.out.println("finish griding...");
		return list;
	}

	private static List<Route> makeGrids(List<Route> routes, int gridNumberX, int gridNumberY) {
		double gridSizeX = (right - left) / gridNumberX;
		double gridSizeY = (top - bottom) / gridNumberY;
		System.out.println(right + "======" + left);
		System.out.println(top + "======" + bottom);
		for (Route r : routes) {
			List<RawPosition> ps = r.getPositions();
			Integer[] prev = new Integer[] { -1, -1 };
			for (RawPosition p : ps) {
				int indexX = (int) Math.floor((p.getLatitude() - left) / gridSizeX);
				int indexY = (int) Math.floor((p.getLongtitude() - bottom) / gridSizeY);
				if (prev[0] == indexX && prev[1] == indexY) {
					continue;
				}
				Integer[] tmp = new Integer[] { indexX, indexY };
				if (prev[0] > 0) {
					while (prev[0] < tmp[0] - 1 && prev[1] < tmp[1] - 1) {
						prev[0] += 1;
						prev[1] += 1;
						r = addGridPositionTORoute(prev, r);
					}

					while (prev[0] < tmp[0] - 1) {
						prev[0] += 1;
						r = addGridPositionTORoute(prev, r);
					}

					while (prev[1] < tmp[1] - 1) {
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
		if (gps.containsKey(tmp[0] + "+" + tmp[1])) {
			gp = gps.get(tmp[0] + "+" + tmp[1]);
			gp.getRoutes().add(r);
			gps.put(tmp[0] + "+" + tmp[1], gp);
		} else {
			gp = new GridPosition(tmp[0], tmp[1]);
			gp.getRoutes().add(r);
			gps.put(tmp[0] + "+" + tmp[1], gp);
		}
		List<GridPosition> grids = r.getGrids();
		grids.add(gp);
		r.setGrids(grids);

		return r;
	}

	private static void write0a(List<Route> list) {
		try{
			StringBuilder sb = new StringBuilder();
			for (Route r : list) {
				List<RawPosition> gps = r.getPositions();
				for(RawPosition rp : gps) {
					sb.append(rp.getLatitude() + " " + rp.getLongtitude() + "\n");
				}
				sb.append("\n");
			}
		    PrintWriter writer = new PrintWriter("route.txt", "UTF-8");
		    writer.println(sb);
		    writer.close();
		} catch (IOException e) {
		   // do something
			System.out.println(e.getMessage() + "!!!!!!");
		}
	}
	
	private static boolean isOut(double lat, double lon) {
		return lat < left || lat > right || lon < bottom || lon > top;
	}
}
