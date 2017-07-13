package PROJECT82.dataProvision.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
