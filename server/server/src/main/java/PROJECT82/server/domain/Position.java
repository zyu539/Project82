package PROJECT82.server.domain;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Embeddable
public class Position {
	
	public Position(double latitude, double longtitude, double speed) {
		super();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.speed = speed;
	}
	
	protected Position() {}

	@XmlElement
	private double latitude;
	
	@XmlElement
	private double longtitude;
	
	@XmlElement
	private double speed;

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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
