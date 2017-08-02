package PROJECT82.dataProvision.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TreeNode {
	
	public TreeNode() {};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
    @JoinColumn(name="LEFT_ID")
	private TreeNode left;
	
	@OneToOne
    @JoinColumn(name="RIGHT_ID")
	private TreeNode right;
	
	private int depth;
	
	@OneToOne
    @JoinColumn(name="NODE_ID")
	private GridPosition gp = new GridPosition();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="root_ID")
	private Forest forest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public GridPosition getGp() {
		return gp;
	}

	public void setGp(GridPosition gp) {
		this.gp = gp;
	}

}
