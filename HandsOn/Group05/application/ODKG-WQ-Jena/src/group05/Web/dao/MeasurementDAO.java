package group05.Web.dao;

import java.util.Collection;
import group05.Web.model.Measurement;

public interface MeasurementDAO {
	public void create(Measurement measurement);
	
	public Collection<Measurement> read();
	
	public void update(Measurement measurement);
	
	public void delete(Measurement measurement);
}
