package PROJECT82.server.service;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import PROJECT82.server.dao.RouteDao;
import PROJECT82.server.dao.UserDao;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RouteDao.class).to(RouteDao.class);
        bind(UserDao.class).to(UserDao.class);
    }
}
