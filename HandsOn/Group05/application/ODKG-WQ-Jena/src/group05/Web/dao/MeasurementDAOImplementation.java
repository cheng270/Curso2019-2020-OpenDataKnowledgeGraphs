package group05.Web.dao;

import java.util.Collection;
import javax.ws.rs.core.NewCookie;
import org.hibernate.Session;
import group05.Web.model.Measurement;

public class MeasurementDAOImplementation implements MeasurementDAO {
	private static MeasurementDAOImplementation instance = null;
	private MeasurementDAOImplementation() {
		
	}
	public static MeasurementDAOImplementation getInstance() {
		if(instance == null) {
			instance = new MeasurementDAOImplementation();
		}
		return instance;
	}

	@Override
	public void create(Measurement measurement) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.save(measurement);
		session.getTransaction().commit();
		session.close();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<Measurement> read() {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Collection<Measurement> measurements = session.createQuery( "from Measurement" ).list(); // here
		session.getTransaction().commit();
		session.close();
		return measurements;
	}
	
	@Override
	public void update(Measurement measurement) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(measurement);
		session.getTransaction().commit();
		session.close();
	}
	
	@Override
	public void delete(Measurement measurement) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(measurement);
		session.getTransaction().commit();
		session.close();
	}
}