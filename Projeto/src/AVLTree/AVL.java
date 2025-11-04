
package AVLTree;

public class AVL extends BST {

	protected Node root;

	public AVL() {
		this(null);
	}

	public AVL(Node root) {
		this.root = root;
	}

	public Node insert(Node node, Node parent, double data) {
		return balance(insert(node, parent, data));
	}

	protected Node insert(Node node, Node parent, int data) {
		if (node == null) {
			return new Node(data, parent);
		}

		int diff = data - node.getData();
		
		if (diff < 0) {
			node.setLeft(insert(node.getLeft(), node, data));
		} else if (diff > 0) {
			node.setRight(insert(node.getRight(), node, data));
		} else {
			// Nessa implementação, não é permitida a inserção de duplicatas na BST.
			// Portanto, não fazemos nada (esse else pode inclusive ser removido...).
			throw new RuntimeException("Essa BST não pode ter duplicatas!");
		}
		
		return node;
	}
	
	public Node remove(Node node, double data) {
		return balance(remove(node, data));
	}

	protected Node remove(Node node, int data) {
		if (node == null) {
			//return null;
			throw new RuntimeException("Nó com chave " + data + " não existe na BST!");
		}
		
		int diff = data - node.getData();
				
		if (diff < 0) {
			node.setLeft(remove(node.getLeft(), data));
		} else if (diff > 0) {
			node.setRight(remove(node.getRight(), data));
		} else {
			node = removeNode(node);
		}
		
		return node;		
	}
	
	private Node removeNode(Node node) {
		if (node.isLeaf()) {
			return null;
		}
		
		if (!node.hasLeftChild()) {
			return node.getRight();
		} else if (!node.hasRightChild()) {
			return node.getLeft();
		} else {
			Node predecessor = findPredecessor(node.getData());
			node.setData(predecessor.getData());
			node.setLeft(remove(node.getLeft(), predecessor.getData()));
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
