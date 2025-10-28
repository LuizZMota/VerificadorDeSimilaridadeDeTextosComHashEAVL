
package AVLTree;

public class AVL extends BST {

	public AVL() {
		super();
	}

	public AVL(Node root) {
		super(root);
	}

	@Override
	protected Node insert(Node node, Node parent, int data) {
		return balance(super.insert(node, parent, data));
	}
	
	@Override
	protected Node remove(Node node, int data) {
		return balance(super.remove(node, data));
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
		
		// Troca as conexões do nó pai (newRoot vira filho de parent, no lugar de node).
		Node parent = node.getParent();
		updateParentChild(parent, node, newRoot);
		
		// newRoot é a nova raiz desta subárvore, então seu filho esquerdo se torna o
		// filho direito de node (que deixa de ser raiz desta subárvore).
		Node left = newRoot.getLeft();
		node.setRight(left);

		// node agora vira filho esquerdo de newRoot.
		newRoot.setLeft(node);
		
		return newRoot;
	}
	
	// Rotação RR.
	private Node rotateRight(Node node) {
		if (node == null) {
			return null;
		}
		
		// O nó atual deve ter um filho esquerdo, que será a nova raiz desta subárvore.
		Node newRoot = node.getLeft();
		if (newRoot == null) {
			return null;
		}
		
		// Troca as conexões do nó pai (newRoot vira filho de parent, no lugar de node).
		Node parent = node.getParent();
		updateParentChild(parent, node, newRoot);
		
		// newRoot é a nova raiz desta subárvore, então seu filho direito se torna o
		// filho esquerdo de node (que deixa de ser raiz desta subárvore).
		Node right = newRoot.getRight();
		node.setLeft(right);
		
		// node agora vira filho direito de newRoot.
		newRoot.setRight(node);
		
		return newRoot;
	}
	
	// Rotação LR.
	private Node rotateLeftRight(Node node) {
		node.setLeft(rotateLeft(node.getLeft()));
		return rotateRight(node);
	}
	
	// Rotação RL.
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
