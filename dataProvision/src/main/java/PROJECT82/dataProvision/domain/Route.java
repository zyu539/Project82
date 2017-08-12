package PROJECT82.dataProvision.domain;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Route {

	public Route() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@OneToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
//	@JoinColumn(name= "route_id")
	protected transient List<RawPosition> positions = new ArrayList<RawPosition>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "route_grid", joinColumns = {
			@JoinColumn(name = "route_ID", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "grid_ID", referencedColumnName = "id") })
	protected List<GridPosition> grids = new ArrayList<GridPosition>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<GridPosition> getGrids() {
		return grids;
	}

	public void setGrids(List<GridPosition> grids) {
		this.grids = grids;
	}

	public List<RawPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<RawPosition> positions) {
		this.positions = positions;
	}
}
