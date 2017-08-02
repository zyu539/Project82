package PROJECT82.dataProvision;

import java.util.List;

import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;
import PROJECT82.dataProvision.service.RouteService;
import PROJECT82.dataProvision.service.SampleService;
import PROJECT82.dataProvision.util.Algorithms;

public class Main {
	
	public static void main(String[] args) {
		SampleService ss = new SampleService();
		
		RouteService rs = new RouteService();
		ss.persistRawData();
//		List<RawPosition> plist = ss.retrieveOrderedData();
//		ss.persistRawData(plist);
		System.out.println("finish persisting");
		List<Route> tmp = rs.retrieveRoute(new int[]{801, 493}, new int[]{801, 494});
		tmp.stream().map(e -> e.getId() + " ").forEach(System.out::print);
		Algorithms alg = new Algorithms(3, 2);
		System.out.println(alg.IBatAlg(tmp, tmp.get(0)));
		System.out.println(alg.IBatAlg(tmp, tmp.get(1)));
		System.out.println(alg.IBatAlg(tmp, tmp.get(2)));
		System.out.println(alg.IBatAlg(tmp, tmp.get(3)));
		System.out.println(alg.IBatAlg(tmp, tmp.get(4)));
		return;
	}
	
	
}
