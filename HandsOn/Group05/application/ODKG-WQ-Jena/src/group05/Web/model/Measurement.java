package group05.Web.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Measurement implements Serializable {
	@Id
	private String meas;
	
	@ManyToOne
	private SamplePoint samplePoint;
	
	@ManyToOne
	private Determinand determinand;
	
	private double result;
	private String materialType;
	private String purpose;
	private String dateTime;
	private boolean isCompliance;
	
	public Measurement() {
		
	}
	
	public String getMeas() {
		return meas;
	}
	
	public SamplePoint getSamplePoint() {
		return samplePoint;
	}
	
	public Determinand getDeterminand() {
		return determinand;
	}
	
	public double getResult() {
		return result;
	}
	
	public String getMaterialType() {
		return materialType;
	}
	
	public String getPurpose() {
		return purpose;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public boolean getIsCompliance() {
		return isCompliance;
	}
	
	public void setMeas(String meas) {
		this.meas = meas;
	}
	
	public void setSamplePoint(SamplePoint samplePoint) {
		this.samplePoint = samplePoint;
	}
	
	public void setDeterminand(Determinand determinand) {
		this.determinand = determinand;
	}
	
	public void setResult(double result) {
		this.result = result;
	}
	
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setIsCompliance(boolean isCompliance) {
		this.isCompliance = isCompliance;
	}
}
