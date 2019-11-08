package group05.Web.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SamplePoint implements Serializable {
	@Id
	private String SP;
	
	@OneToMany(mappedBy = "samplePoint" , fetch = FetchType.EAGER)
	private Collection<Measurement> measurements;
	
	private String label;
	private int northing;
	private int easting;
	private double lat;
	private double lng;
	
	public SamplePoint() {
		
	}
	
	public String getSP() {
		return SP;
	}
	
	public Collection<Measurement> getMeasurements() {
		return measurements;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getNorthing() {
		return northing;
	}
	
	public int getEasting() {
		return easting;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setSP(String SP) {
		this.SP = SP;
	}
	
	public void setMeasurements(Collection<Measurement> measurements) {
		this.measurements = measurements;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setNorthing(int northing) {
		this.northing = northing;
	}
	
	public void setEasting(int easting) {
		this.easting = easting;
	}
	
}
