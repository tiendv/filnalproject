package uit.qass.util.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	static {
		sessionFactory = new  Configuration().configure().buildSessionFactory();
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
