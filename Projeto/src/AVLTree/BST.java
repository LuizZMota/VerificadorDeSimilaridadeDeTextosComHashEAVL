package AVLTree;

public class BST extends BinaryTree {

	public BST() {
		super();
	}

	public BST(Node root) {
		super(root);
	}

	public Node search(double data) {
		return search(root, data);
	}

	private Node search(Node node, double data) {
		if (node == null) {
			return null;
		}
		
		double diff = data - node.getData();

		if (diff < 0) {
			return search(node.getLeft(), data);
		} else if (diff > 0) {
			return search(node.getRight(), data);
		} else {
			return node;
		}
	}
	
	public void insert(double data) {
		root = insert(root, null, data);
	}
	
	protected Node insert(Node node, Node parent, double data) {
		if (node == null) {
			return new Node(data, parent);
		}

		double diff = data - node.getData();
		
		if (diff < 0) {
			node.setLeft(insert(node.getLeft(), node, data));
		} else if (diff > 0) {
			node.setRight(insert(node.getRight(), node, data));
		} else {
			
		}
		
		return node;
	}
	
	public void remove(double data) {
		root = remove(root, data);
	}
	
	protected Node remove(Node node, double data) {
		if (node == null) {
			//return null;
			throw new RuntimeException("Nó com chave " + data + " não existe na BST!");
		}
		
		double diff = data - node.getData();
				
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
	
	public Node findMin() {
		return findMin(root);
	}
	
	private Node findMin(Node node) {
		if (node == null) {
			return null;
		}

		while (node.hasLeftChild()) {
			node = node.getLeft();
		}
		return node;
	}
	
	public Node findMax() {
		return findMax(root);
	}
	
	private Node findMax(Node node) {
		if (node == null) {
			return null;
		}

		while (node.hasRightChild()) {
			node = node.getRight();
		}
		return node;
	}
	
	public Node findPredecessor(double data) {
		Node node = search(data);
		return predecessor(node);
	}
	
	private Node predecessor(Node node) {
		if (node == null) {
			return null;
		}
		
		if (node.hasLeftChild()) {
			return findMax(node.getLeft());
		} else {
			Node current = node;
			Node parent = node.getParent();

			while (parent != null && current == parent.getLeft()) {
				current = parent;
				parent = current.getParent();
			}
			
			return parent;
		}
	}
	
	public Node findSuccessor(double data) {
		Node node = search(data);
		return successor(node);		
	}
	
	private Node successor(Node node) {
		if (node == null) {
			return null;
		}

		if (node.hasRightChild()) {
			return findMin(node.getRight());
		} else {
			Node current = node;
			Node parent = node.getParent();

			while (parent != null && current == parent.getRight()) {
				current = parent;
				parent = current.getParent();
			}
			
			return parent;
		}
	}
	
	public void clear() {
		root = clear(root);
	}
	
	private Node clear(Node node) {
		if (node == null) {
			return null;
		}

		node.setLeft(clear(node.getLeft()));
		node.setRight(clear(node.getRight()));
		node.setParent(null);

		return null;
	}

	@Override
	public String toString() {
		return "BST - isEmpty(): " + isEmpty()
				+ ", getDegree(): " + getDegree()
				+ ", getHeight(): " + getHeight()
				+ ", root => { " + root + " }";				
	}

}
