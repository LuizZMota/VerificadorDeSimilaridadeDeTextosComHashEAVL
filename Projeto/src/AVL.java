
public class AVL {

	protected Node root;

	public AVL() {
		this(null);
	}

	public AVL(Node root) {
		this.root = root;
	}

	public Node insert(Node node, Node parent, double data, Resultado resultado) {
		return balance(insert(node, parent, data, resultado));
	}

	protected Node insert(Node node, Node parent, int data, Resultado resultado) {
		if (node == null) {
			return new Node(data, parent, resultado);
		}

		double diff = data - node.getData();
		
		if (diff < 0) {
			node.setLeft(insert(node.getLeft(), node, data, resultado));
		} else if (diff > 0) {
			node.setRight(insert(node.getRight(), node, data, resultado));
		} else {
			node.getResultados().add(resultado);
			return node;
		}
		
		return node;
	}
	
	
	private Node balance(Node node) {
		if (node == null)
			return null;
		
		int nodeBF = node.getBalanceFactor();
		if (nodeBF < -1) {
			if (node.getLeft().getBalanceFactor() <= 0) {
				node = rotateRight(node);
			} else {
				node = rotateLeftRight(node);
			}
		} else if (nodeBF > 1) {
			if (node.getRight().getBalanceFactor() >= 0) {
				node = rotateLeft(node);
			} else {
				node = rotateRightLeft(node);
			}
		}
		
		return node;
	}

	private void updateParentChild(Node parent, final Node child, Node newChild) {
		if (parent != null) {
			if (parent.getLeft() == child) {
				parent.setLeft(newChild);
			} else {
				parent.setRight(newChild);
			}
		} else {
			root = newChild;
			newChild.setParent(null);
		}
	}
	
	private Node rotateLeft(Node node) {
		if (node == null) {
			return null;
		}
		
		Node newRoot = node.getRight();
		if (newRoot == null) {
			return null;
		}
		
		Node parent = node.getParent();
		updateParentChild(parent, node, newRoot);
		
		Node left = newRoot.getLeft();
		node.setRight(left);

		newRoot.setLeft(node);
		
		return newRoot;
	}
	
	private Node rotateRight(Node node) {
		if (node == null) {
			return null;
		}
		
		Node newRoot = node.getLeft();
		if (newRoot == null) {
			return null;
		}
		
		Node parent = node.getParent();
		updateParentChild(parent, node, newRoot);
		
		Node right = newRoot.getRight();
		node.setLeft(right);
		
		newRoot.setRight(node);
		
		return newRoot;
	}
	
	private Node rotateLeftRight(Node node) {
		node.setLeft(rotateLeft(node.getLeft()));
		return rotateRight(node);
	}
	
	private Node rotateRightLeft(Node node) {
		node.setRight(rotateRight(node.getRight()));
		return rotateLeft(node);
	}
	
	@Override
	public String toString() {
		return "AVL - isEmpty(): " + isEmpty()
				+ ", getDegree(): " + getDegree()
				+ ", getHeight(): " + getHeight()
				+ ", root => { " + root + " }";				
	}

}
