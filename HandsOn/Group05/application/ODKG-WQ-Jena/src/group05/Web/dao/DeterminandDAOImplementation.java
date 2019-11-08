package group05.Web.dao;

import java.util.Collection;
import javax.ws.rs.core.NewCookie;
import org.hibernate.Session;
import group05.Web.model.Determinand;

public class DeterminandDAOImplementation implements DeterminandDAO {
	private static DeterminandDAOImplementation instance = null;
	private DeterminandDAOImplementation() {
		
	}
	public static DeterminandDAOImplementation getInstance() {
		if(instance == null) {
			instance = new DeterminandDAOImplementation();
		}
		return instance;
	}
	
	@Override
	public void create(Determinand determinand) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.save(determinand);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Determinand read(int notation) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Determinand determinand = session.load( Determinand.class, notation );
		session.getTransaction().commit();
		session.close();
		return determinand;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Determinand> readAll() {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Collection<Determinand> determinands = session.createQuery( "from Determinand" ).list(); // here
		session.getTransaction().commit();
		session.close();
		return determinands;
	}

	@Override
	public void update(Determinand determinand) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(determinand);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(Determinand determinand) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(determinand);
		session.getTransaction().commit();
		session.close();
	}
}
