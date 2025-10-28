package AVLTree;

// imports para a fila que pode ser usada na levelOrderTraversalHelper(). 

public class BinaryTree {

	private BTNode root;

	public BinaryTree() {
		this(null);
	}

	
	public BTNode getRoot() {
		return root;
	}

	public void isComplete(){
		
	}

	public boolean isFull(){
		return true;		
	}

	public boolean isFullHelper(BTNode node){
		if(node.getDegree()==0){
			return true;
		} else if (node.getDegree()==2){
			return true;
		} else {
			return false;
		}
	}

	public void setRoot(BTNode root) {
		this.root = root;
	}

	public BinaryTree(BTNode root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int getDegree() {
		return getDegreeHelper(root);
	}

	private int getDegreeHelper(BTNode node) {
		//TODO
		if (node == null){
			return 0;
		} 
		int currentDegree = 0;
		if(node.getLeft()!=null){
			currentDegree++;
		}
		if(node.getRight()!=null){
			currentDegree++;
		}
		int leftDegree = getDegreeHelper(node.getLeft());
    	int rightDegree = getDegreeHelper(node.getRight());

		return Math.max(currentDegree, Math.max(leftDegree, rightDegree));
	}

	public int getHeight(BTNode node) {
		if (node == null) {
			return -1;
		}

		return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
	}

	public String inOrderTraversal() {
		return inOrderTraversalHelper(root);
	}

	private String inOrderTraversalHelper(BTNode node) {
		if (node == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		
		sb.append(postOrderTraversalHelper(node.getLeft()));
		sb.append(node.getData() + " ");
		sb.append(postOrderTraversalHelper(node.getRight()));
		return sb.toString();
	}

	public String preOrderTraversal() {
		return preOrderTraversalHelper(root);
	}

	private String preOrderTraversalHelper(BTNode node) {
		//TODO
		if (node == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		
		sb.append(node.getData() + " ");
		sb.append(postOrderTraversalHelper(node.getLeft()));
		sb.append(postOrderTraversalHelper(node.getRight()));
		return sb.toString();
	}

	public String postOrderTraversal() {
		return postOrderTraversalHelper(root);
	}

	private String postOrderTraversalHelper(BTNode node) {
		if (node == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		
		sb.append(postOrderTraversalHelper(node.getLeft()));
		sb.append(postOrderTraversalHelper(node.getRight()));
		sb.append(node.getData() + " ");
		
		return sb.toString();
	}

	/* public String levelOrderTraversal() {
		return levelOrderTraversalHelper(root);
	} */
	
	/* private String levelOrderTraversalHelper(BTNode node) {
		//TODO
	} */

	@Override
	public String toString() {
		return "BinaryTree - isEmpty(): " + isEmpty()
				+ ", getDegree(): " + getDegree()
				+ ", getHeight(): " + getHeight(root)
				+ ", root => { " + root + " }";				
	}



}