package PROJECT82.dataProvision.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import PROJECT82.dataProvision.domain.RawPosition;
import PROJECT82.dataProvision.domain.Route;

public class RouteUtil {
	
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	}
	
	public static double calSpeed(double lat1, double lng1, double lat2, double lng2, double timeInSec) {
		return distFrom(lat1, lng1, lat2, lng2) / timeInSec;
	}
	
	public static List<Route> pickNRandom(List<Route> lst, int n) {
	    List<Route> copy = new ArrayList<Route>(lst);
	    Collections.shuffle(copy);
	    return copy.subList(0, n);
	}
	
	public static long StringToSec(String date) {
		long sec = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date d = sdf.parse(date);
			sec = d.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sec;
	}
	
	public static List<RawPosition> orderPosition(List<RawPosition> ps) {
		Collections.sort(ps, (RawPosition p1, RawPosition p2) ->{
			if(p1.getTaxiId() - p2.getTaxiId() == 0) {
				return p1.getDate().compareTo(p2.getDate());
			} else {
				return p1.getTaxiId() - p2.getTaxiId();
			}
		});
		return ps;
	}
}
