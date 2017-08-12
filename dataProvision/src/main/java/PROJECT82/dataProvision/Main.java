package PROJECT82.dataProvision;

import java.util.ArrayList;
import java.util.List;

import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;
import PROJECT82.dataProvision.service.RouteService;
import PROJECT82.dataProvision.service.SampleService;
import PROJECT82.dataProvision.util.Algorithms;
import PROJECT82.dataProvision.util.MapGriding;

public class Main {

	public static void main(String[] args) {
		SampleService ss = new SampleService();

		RouteService rs = new RouteService();
//		ss.persistRawData();
//		MapGriding.mostVisit(rs.retrieveRoute());
		List<Route> tmp = rs.retrieveRoute(new int[] { 45, 39 }, new int[] { 39, 27 });
		tmp = MapGriding.subtract(tmp, new int[] { 45, 39 }, new int[] { 39, 27 });
		System.out.println(tmp.size());
//		MapGriding.write0b(tmp, 75, 75);
		Algorithms alg = new Algorithms(40, 40);
		//rs.persistForest(alg.trainIForest(tmp));
		List<Route> tmp1 = new ArrayList<Route>();
		int count = 1;
		for (Route r : tmp) {
//			r.getGrids().stream().map(e -> e.getId() + " " + e.getIndexX() + " " + e.getIndexY() + "\n")
//					.forEach(System.out::print);
			System.out.print(count + ": ");
			count++;
			double score = alg.IBatAlg(tmp, r);
			System.out.println(r.getId() + "  " + score + "\n");
			if (score > 0.43) {
				tmp1.add(r);
			}
		}
		MapGriding.write0b(tmp1, 75, 75);
		return;
	}

}
