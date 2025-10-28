package AVLTree;

public class BTNode {

	private double data, balanceFactor;
	private BTNode parent;
	private BTNode left;
	private BTNode right;
	
	public BTNode() {
		this(0, null);
	}

	public BTNode(double data) {
		this(data, null);
	}

	public BTNode(double data, BTNode parent) {
		this.data = data;
		this.parent = parent;
		this.left = null;
		this.balanceFactor = 0;
		this.right = null;
	}

	public double getBalanceFactor(){
		return this.balanceFactor;
	}

	public void setBalanceFactor(double data){
		this.balanceFactor = data;
	}

	public void updateBalanceFactor(){
		int leftHeight = hasLeftChild () ? left . getHeight () : -1;
 		int rightHeight = hasRightChild () ? right . getHeight () : -1;
 		balanceFactor = rightHeight - leftHeight ;
	} 

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public BTNode getParent() {
		return parent;
	}

	public void setParent(BTNode parent) {
		this.parent = parent;
	}

	public BTNode getLeft() {
		return left;
	}

	public void setLeft(BTNode left) {
    this.left = left;
    // Correção para setLeft
    if (left != null) { 
        left.setParent(this);
    }
}

	public BTNode getRight() {
		return right;
	}

	public void setRight(BTNode right) {
    this.right = right;
    // Correção para setRight
    if (right != null) {
        right.setParent(this);
    }
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
		//TODO
		return getRight() == null && getLeft() == null;
	}

	public int getDegree() {
		int count = 0;
		if(hasLeftChild()){
			count++;
		}
		if(hasRightChild()){
			count++;
		}
		return count;
	}

	public int getLevel() {
		//TODO
		if (this.getParent() == null) {
            return 0;
        }
        return 1 + this.getParent().getLevel();
	}

	public int getHeight() {
		if (right == null && left == null){
			return 0;
		}
		int leftnode;
		if (this.getLeft() == null){
			leftnode = 0;
		} else {
			leftnode = this.getLeft().getHeight();
		}
		int rightnode;
		if (this.getRight() == null){
			rightnode = 0;
		} else {
			rightnode = this.getRight().getHeight();
		}

		return 1 + Math.max(leftnode, rightnode);
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
				+ ", getHeight(): " + getHeight();
	}

}