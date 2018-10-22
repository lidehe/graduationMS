package history;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

final public class HistoryIuimSesssionFactory {

	private static ServiceRegistry serviceRegistry = null;
	private static Configuration cfg = null;
	private static SessionFactory sessionFactory = null;


	public static SessionFactory getSessionFactory(String fileName) {
		cfg = new Configuration().configure(fileName);
		// cfg = new Configuration().configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		System.out.println("能否更改数据库连接呢？如果能，则会使得被访问的数据库里被框架建很多表"+fileName);
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}
}