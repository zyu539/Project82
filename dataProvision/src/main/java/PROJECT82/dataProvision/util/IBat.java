package PROJECT82.dataProvision.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import PROJECT82.dataProvision.domain.GridPosition;
import PROJECT82.dataProvision.domain.Route;

public class IBat {
	
	public static double IBatAlg(List<Route> list, Route test, int numOfTrials, int sampleSize) {
		int[] n = new int[numOfTrials];
		Random rand = new Random();
		List<GridPosition> t = test.getGrids();
		final double cN = 2 * harmonic(sampleSize - 1) - 2 * (sampleSize - 1) / sampleSize;
		for (int i = 0; i < numOfTrials; i++) {
			List<Route> sample = RouteUtil.pickNRandom(list, sampleSize);
			while (!sample.isEmpty() && !t.isEmpty()) {
				n[i]++;
				int k = rand.nextInt(t.size());
				GridPosition p = t.remove(k);
				List<Route> tmp = new ArrayList<Route>();
				for (Route r : sample) {
					List<GridPosition> gp = r.getGrids();
					if (!gp.contains(p)) {
						tmp.add(r);
					}
				}
				sample.removeAll(tmp);
			}
		}
		
		return IntStream.of(n).sum() / (numOfTrials * cN);
	}
	
	private static double harmonic(int n) {
		return Math.log(n) + 0.57721566;
	}

}
