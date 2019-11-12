package group05.Web.dao;

import java.util.Collection;
import group05.Web.model.Determinand;

public interface DeterminandDAO {
	public void create(Determinand determinand);
	
	public Determinand read(int notation);
	
	public Collection<Determinand> readAll();
	
	public void update(Determinand determinand);
	
	public void delete(Determinand determinand);
}
