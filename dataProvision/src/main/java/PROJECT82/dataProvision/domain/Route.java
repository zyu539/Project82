package PROJECT82.dataProvision.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Route {

	public Route() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection(fetch = FetchType.LAZY, targetClass = RawPosition.class)
	@CollectionTable(name = "Positions")
	protected List<RawPosition> positions = new ArrayList<RawPosition>();

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "route_grid", joinColumns = {
			@JoinColumn(name = "route_ID", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "grid_ID", referencedColumnName = "id") })
	protected List<GridPosition> grids = new ArrayList<GridPosition>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="route_ID")
	private TreeNode node;

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

	public TreeNode getNode() {
		return node;
	}

	public void setNode(TreeNode node) {
		this.node = node;
	}
}
