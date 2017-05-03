package PROJECT82.server.service;

import org.glassfish.jersey.server.ResourceConfig;

import PROJECT82.server.domain.PersistenceManager;

public class RouteApplication extends ResourceConfig{
	
	public RouteApplication() {
		register(new ApplicationBinder());
		register(PersistenceManager.instance());
        packages(true, "PROJECT82.server.service");
	}
	
}
