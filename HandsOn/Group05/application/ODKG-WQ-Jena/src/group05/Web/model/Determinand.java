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
public class Determinand implements Serializable {
	@Id
	private int notation;
	
	@OneToMany(mappedBy = "determinand" , fetch = FetchType.EAGER)
	private Collection<Measurement> measurements;
	
	private String label;
	private String definition;
	private String unit;
	private String sameAs;
	
	public Determinand() {
		
	}
	
	public int getNotation() {
		return notation;
	}
	
	public Collection<Measurement> getMeasurements() {
		return measurements;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public String getSameAs() {
		return sameAs;
	}
	
	public void setNotation(int notation) {
		this.notation = notation;
	}
	
	public void setMeasurements(Collection<Measurement> measurements) {
		this.measurements = measurements;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}
}
