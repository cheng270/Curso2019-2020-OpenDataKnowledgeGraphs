package group05.Web.dao;

import java.util.Collection;
import group05.Web.model.SamplePoint;

public interface SamplePointDAO {
	public void create(SamplePoint samplePoint);
	
	public SamplePoint read(String SP);
	
	public Collection<SamplePoint> readAll();
	
	public void update(SamplePoint samplePoint);
	
	public void delete(SamplePoint samplePoint);

}
