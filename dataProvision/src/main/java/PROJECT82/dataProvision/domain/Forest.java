package PROJECT82.dataProvision.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Forest {

	public Forest() {};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy="forest", cascade = CascadeType.MERGE)
	private List<TreeNode> roots = new ArrayList<TreeNode>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<TreeNode> getRoots() {
		return roots;
	}

	public void setRoots(List<TreeNode> roots) {
		this.roots = roots;
	}

	
}
