
import java.util.ArrayList;
import java.util.List;

public class Node {

	protected double data;
	public List<Resultado> resultados;
	private int balanceFactor;
	protected Node parent;
	protected Node left;
	protected Node right;

	public Node(double data, Node parent) {
		this.data = data;
		this.resultados = new ArrayList<>();
		this.balanceFactor = 0;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}

	public List<Resultado> getResultados(){
		return this.resultados;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
		
		if (this.left != null) {
			this.left.setParent(this);
		}

		updateBalanceFactor();
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
		
		if (this.right != null) {
			this.right.setParent(this);
		}
		
		updateBalanceFactor();
	}

	public boolean hasLeftChild() {
		return left != null;
	}

	public boolean hasRightChild() {
		return right != null;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public int getDegree() {
		int degree = 0;
		
		if (hasLeftChild()) {
			++degree;
		}
		
		if (hasRightChild()) {
			++degree;
		}
		
		return degree;
	}

	public int getLevel() {
		if (isRoot()) {
			return 0;
		}

		return parent.getLevel() + 1;
	}

	public int getHeight() {
		if (isLeaf()) {
			return 0;
		}

		int height = 0;
		
		if (hasLeftChild()) {
			height = Math.max(height, left.getHeight());
		}
		
		if (hasRightChild()) {
			height = Math.max(height, right.getHeight());
		}
		
		return height + 1;
	}

	public int getBalanceFactor() {
		return balanceFactor;
	}

	private void updateBalanceFactor() {
		int leftHeight = hasLeftChild() ? left.getHeight() : -1;
		int rightHeight = hasRightChild() ? right.getHeight() : -1;
		balanceFactor = rightHeight - leftHeight;
	}

	@Override
	public String toString() {
		return "data: " + data
				+ ", parent: " + (parent != null ? parent.getData() : "null")
				+ ", left: " + (left != null ? left.getData() : "null")
				+ ", right: " + (right != null ? right.getData() : "null")
				+ ", isRoot(): " + isRoot()
				+ ", isLeaf(): " + isLeaf()
				+ ", getDegree(): " + getDegree()
				+ ", getLevel(): " + getLevel()
				+ ", getHeight(): " + getHeight()
				+ ", getBalanceFactor(): " + getBalanceFactor();
	}

}
