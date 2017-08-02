package PROJECT82.server.util;

import java.util.ArrayList;
import java.util.List;

import PROJECT82.server.domain.Position;
import PROJECT82.server.domain.Route;

public class MapGriding {

	public static List<Integer[]> makeGrids(Route r, double gridSizeX, double gridSizeY, double left, double bottom) {
		List<Integer[]> list = new ArrayList<Integer[]>();
		List<Position> ps = r.getPositions();
		Integer[] prev = new Integer[] {-1, -1};
		for (Position p : ps) {
			int indexX = (int) Math.floor((p.getLatitude() - left) / gridSizeX);
			int indexY = (int) Math.floor((p.getLongitude() - bottom) / gridSizeY);
			Integer[] tmp = new Integer[] { indexX, indexY };
			if (prev[0] > 0) {
				while (prev[0] < tmp[0]-1 && prev[1] < tmp[1]-1) {
					prev[0] += 1;
					prev[1] += 1;
					list.add(prev);
				}
					
				while (prev[0] < tmp[0]-1) {
					prev[0] += 1;
					list.add(prev);
				}
					
				while (prev[1] < tmp[1]-1) {
					prev[1] += 1;
					list.add(prev);
				}
					
			}
			list.add(tmp);
			prev = tmp;
		}

		return list;
	}
}
