package group05.Web.dao;

import java.util.Collection;
import javax.ws.rs.core.NewCookie;
import org.hibernate.Session;

import group05.Web.model.Determinand;
import group05.Web.model.SamplePoint;

public class SamplePointDAOImplementation implements SamplePointDAO {
	private static SamplePointDAOImplementation instance = null;
	private SamplePointDAOImplementation() {
		
	}
	public static SamplePointDAOImplementation getInstance() {
		if(instance == null) {
			instance = new SamplePointDAOImplementation();
		}
		return instance;
	}
	
	@Override
	public void create(SamplePoint samplePoint) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.save(samplePoint);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public SamplePoint read(String SP) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		SamplePoint samplePoint = session.load( SamplePoint.class, SP );
		session.getTransaction().commit();
		session.close();
		return samplePoint;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<SamplePoint> readAll() {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Collection<SamplePoint> samplePoints = session.createQuery( "from SamplePoint" ).list(); // here
		session.getTransaction().commit();
		session.close();
		return samplePoints;
	}

	@Override
	public void update(SamplePoint samplePoint) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(samplePoint);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void delete(SamplePoint samplePoint) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(samplePoint);
		session.getTransaction().commit();
		session.close();
	}
}
