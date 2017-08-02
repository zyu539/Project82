package PROJECT82.server.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Embeddable
public class Position {

	public Position(double latitude, double longitude, String time) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
	}

	protected Position() {}

	@XmlElement
	private double latitude;

	@XmlElement
	private double longitude;

	@XmlElement
	private double speed;

	@XmlElement
	@Column(nullable = false)
	private String time;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
