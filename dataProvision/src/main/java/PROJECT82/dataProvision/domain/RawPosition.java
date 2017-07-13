package PROJECT82.dataProvision.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RawPosition {
	
	public RawPosition(int taxiId, double latitude, double longtitude, int onService, String date) {
		super();
		this.taxiId = taxiId;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.onService = onService;
		this.date = date;
	}
	
	public RawPosition(double latitude, double longtitude, String date) {
		super();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.onService = 1;
		this.date = date;
	}
	
	protected RawPosition() {}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int taxiId;

	private double latitude;

	private double longtitude;
	
	private int onService;
	
	@Column(nullable = false)
	private String date;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public int getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public int getOnService() {
		return onService;
	}

	public void setOnService(int onService) {
		this.onService = onService;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
