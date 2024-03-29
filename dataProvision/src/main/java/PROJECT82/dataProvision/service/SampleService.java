package PROJECT82.dataProvision.service;

import java.util.List;

import PROJECT82.dataProvision.dao.SampleDao;
import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;
import PROJECT82.dataProvision.util.MapGriding;
import PROJECT82.dataProvision.util.SampleReader;

public class SampleService {
	
	SampleDao sd = new SampleDao();
	
	public void persistRawData() {
		List<RawPosition> plist = SampleReader.readRoute("/Users/lhy/Downloads/交通赛数据_上/20140803_train.txt");
		System.out.println("lalalala");
		sd.persistData(plist);
	}
	
	public List<RawPosition> retrieveOrderedData() {
		List<RawPosition> plist = sd.retrieveOrderedData();
		return plist;
	}
	
	public void persistRawData(List<RawPosition> list) {
		List<Route> plist = MapGriding.makeRoutes(list);
		System.out.println("lol-jinx");
		sd.persistRoute(plist);
	}
}
