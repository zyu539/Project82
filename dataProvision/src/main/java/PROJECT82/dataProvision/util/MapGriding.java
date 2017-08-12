package PROJECT82.dataProvision.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
			} else if (rp.getOnService() == 1 && (time - prevT) > 24000) {
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
		distFilter(list);
		System.out.println("making grids....");
		makeGrids(list, 75, 75);
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
		write0b(routes, gridNumberX, gridNumberY);
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
	
	public static void write0b(List<Route> list, int gridNumberX, int gridNumberY) {
		try {
			StringBuilder sb = new StringBuilder();
			System.out.println("Start Writing...");
			double gridSizeX = (right - left) / gridNumberX;
			double gridSizeY = (top - bottom) / gridNumberY;
			for (Route r : list) {
				List<GridPosition> gps = r.getGrids();
				for(GridPosition rp : gps) {
					double lat = left + gridSizeX * (rp.getIndexX() + 0.5); 
					double lon = bottom + gridSizeY * (rp.getIndexY() + 0.5);
					sb.append(lat + " " + lon + "\n");
				}
				sb.append("\n");
			}
			PrintWriter writer;
			writer = new PrintWriter("route.txt", "UTF-8");
			writer.println(sb);
		    writer.close();
		    System.out.println("Finish Writing.");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<Route> distFilter(List<Route> list) {
		List<Double> disList = new ArrayList<Double>();
		List<Route> remove = new ArrayList<Route>();
		for (Route r : list) {
			RawPosition prev = null;
			double dist = 0;
			List<RawPosition> gps = r.getPositions();
			for(RawPosition rp : gps) {
				if (prev != null) {
					dist += RouteUtil.distFrom(prev.getLatitude(), prev.getLongtitude(), rp.getLatitude(), rp.getLongtitude());
				}
				prev = rp;
			}
			disList.add(dist);
			if (dist > 16000) {
				remove.add(r);
			}
		}
		list.removeAll(remove);
		Collections.sort(disList);
//		double min = disList.get(0);
//		double max = disList.get(disList.size() - 1);
//		double interval = (max - min) / 100;
//		int[] counts = new int[100];
//		for (Double d : disList) {
//			for (int i = 0; i < 100; i++) {
//				if (d <= min + (i+1) * interval) {
//					counts[i] += 1;
//					break;
//				}
//			}
//		}
//		for (int i = 0; i < 100; i++) {
//			System.out.println((min + i * interval) + " - " + (min + (i+1) * interval)); 
//		}
//		
//		for (int i = 0; i < 100; i++) {
//			System.out.println(counts[i]); 
//		}
		
		return list;
	}
	
	private static boolean isOut(double lat, double lon) {
		return lat < left || lat > right || lon < bottom || lon > top;
	}
	
	public static void mostVisit(List<Route> list) {
		Map<GridPosition, Integer> map = new HashMap<GridPosition, Integer>();
		int max = 0;
		GridPosition index = null;
		for (Route r : list) {
			for (GridPosition p : r.getGrids()) {
				if (map.containsKey(p)) {
					map.replace(p, map.get(p) + 1);
				} else {
					map.put(p,  1);
				}
				if (map.get(p) > max) {
					max = map.get(p);
					index = p;
				}
			}
		}
		System.out.println(index.getId() + "   " + max);
		
		Long id = index.getId();
		max = 0;
		Map<GridPosition, Integer> map2 = new HashMap<GridPosition, Integer>();
		List<Route> routes = index.getRoutes();
		for (Route r : routes) {
			for (GridPosition p : r.getGrids()) {
				if (map2.containsKey(p)) {
					map2.replace(p, map2.get(p) + 1);
				} else {
					map2.put(p,  1);
				}
			}
		}
		
		for (GridPosition gp : map2.keySet()) {
			if (map2.get(gp) > 100 && map2.get(gp) < 300) {
				System.out.println(gp.getIndexX() + " & " + gp.getIndexY() + ": " + map2.get(gp));
			}
		}
	}
	
	public static List<Route> subtract(List<Route> list, int[] start, int[] end) {
		for (Route r : list) {
			List<GridPosition> tmp = r.getGrids();
			List<GridPosition> tmp1 = new ArrayList<GridPosition>();
			boolean flag = false;
			for (GridPosition gp : tmp) {	
				if (gp.getIndexX() == start[0] && gp.getIndexY() == start[1]) {
					flag = true;
				}
				
				if (flag) {
					tmp1.add(gp);
				}
				
				if (gp.getIndexX() == end[0] && gp.getIndexY() == end[1]) {
					tmp1.add(gp);
					break;
				}
				
			}
			r.setGrids(tmp1);
		}
		
		return list;
	}
}
