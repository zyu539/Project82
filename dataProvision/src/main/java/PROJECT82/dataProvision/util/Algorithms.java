package PROJECT82.dataProvision.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import PROJECT82.dataProvision.domain.Forest;
import PROJECT82.dataProvision.domain.GridPosition;
import PROJECT82.dataProvision.domain.Route;
import PROJECT82.dataProvision.domain.TreeNode;

public class Algorithms {
	
	private int numOfTrials; 
	private int sampleSize;
	
	public Algorithms(int numOfTrials, int sampleSize) {
		this.numOfTrials = numOfTrials;
		this.sampleSize = sampleSize;
	}
	
	public double IBatAlg(List<Route> list, Route test) {
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
		
		return Math.pow(2, -(IntStream.of(n).sum() / (numOfTrials * cN)));
	}
	
	public Forest trainIForest(List<Route> list) {
		Forest f = new Forest();
		Random rand = new Random();
		List<TreeNode> tn = new ArrayList<TreeNode>();
		for (int i = 0; i < numOfTrials; i++) {
			List<Route> sample = RouteUtil.pickNRandom(list, sampleSize);
			Set<GridPosition> set = new HashSet<GridPosition>();
			for (Route r : sample) {
				set.addAll(r.getGrids());
			}
			List<GridPosition> list1 = new ArrayList<GridPosition>(set);
			tn.add(divideToTree(list, list1, rand, 1));
		}
		f.setRoots(tn);
		return f;
	}
	
	private double harmonic(int n) {
		return Math.log(n) + 0.57721566;
	}
	
	private TreeNode divideToTree(List<Route> list, List<GridPosition> grids, Random rand, int depth) {
		TreeNode t = new TreeNode();
		t.setRoutes(list);
		if (list.size() < 2) {
			t.setDepth(depth);
			return t;
		}
		GridPosition g = grids.get(rand.nextInt(grids.size()));
		List<Route> left = new ArrayList<Route>();
		List<Route> right = new ArrayList<Route>();
		for (Route r : list) {
			if (r.getGrids().contains(g)) {
				left.add(r);
			} else {
				right.add(r);
			}
		}
		t.setLeft(divideToTree(left, grids, rand, depth + 1));
		t.setRight(divideToTree(right, grids, rand, depth + 1));
		return t;
	}

}
