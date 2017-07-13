package PROJECT82.dataProvision;

import java.util.List;

import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;
import PROJECT82.dataProvision.service.RouteService;
import PROJECT82.dataProvision.service.SampleService;

public class Main {
	
	public static void main(String[] args) {
		SampleService ss = new SampleService();
		RouteService rs = new RouteService();
		ss.persistRawData();
		List<RawPosition> plist = ss.retrieveOrderedData();
		ss.persistRawData(plist);
		List<Route> tmp = rs.retrieveRoute(new int[]{703, 776}, new int[]{703, 776});
		tmp.stream().map(e -> e.getId() + " ").forEach(System.out::print);
		
		return;
	}
	
	
}
