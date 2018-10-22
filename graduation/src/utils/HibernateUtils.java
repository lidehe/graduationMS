package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public final class HibernateUtils {

	private static SessionFactory sessionFactory;

	private HibernateUtils() {
	}

	static {
		try {

			/** Hibernate4取得SessionFactory的方法 */
			Configuration config = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties()).build();// 推荐
			sessionFactory = config.buildSessionFactory(serviceRegistry);
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
