package PROJECT82.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Route {
	
	public Route (Set<Position> route) {
		super();
		this.route = route;
	}
	
	protected Route() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlAttribute
	private Long id;
	
	@ElementCollection(fetch = FetchType.LAZY, targetClass = Position.class)
	@CollectionTable(name = "Position")
	@XmlElementWrapper(name="route")
	@XmlElement(name="position")
	protected Set<Position> route = new HashSet<Position>();
	
	@XmlAttribute
	private int period;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Position> getRoute() {
		return route;
	}

	public void setRoute(Set<Position> route) {
		this.route = route;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}
