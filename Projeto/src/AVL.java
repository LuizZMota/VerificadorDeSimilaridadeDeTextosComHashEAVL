import java.util.List;

public class AVL {

	protected Node root;

	public AVL() {
		this(null);
	}

	public AVL(Node root) {
		this.root = root;
	}

	

	public void maiorSimilaridade(Node atual, double limiar){
		if(atual != null){
			maiorSimilaridade(atual.getLeft(), limiar);
			maiorSimilaridade(atual.getRight(), limiar);
			if(atual.getData() >= limiar){
				List<Resultado> res = atual.getResultados();
				for (Resultado r : res) {
					Logger.log(r.toString());
				}
			}
		}
	}

	public void topSimilaridades(Node atual, List<Resultado> lista, double limiar){
		if (atual == null) return;

		topSimilaridades(atual.getLeft(), lista, limiar);
		topSimilaridades(atual.getRight(), lista, limiar);

		if (atual.getData() >= limiar) {
			lista.addAll(atual.getResultados());
		}
	}

	public void menorSimilaridade(Node atual){
		double menor = buscarMenor(atual);
		search(atual, menor);
	}

	private void search(Node node, double data) {
		if (node == null) {
			return;
		}
		
		double diff = data - node.getData();

		if (diff < 0) {
			 search(node.getLeft(), data);
		} else if (diff > 0) {
			search(node.getRight(), data);
		} else {
			List<Resultado> res = node.getResultados();
			for (Resultado r : res) {
				Logger.log(r.toString());
			}
		}	
	}

	private double buscarMenor(Node atual){
		if (atual == null) return Double.MAX_VALUE;

		double menorEsq = buscarMenor(atual.getLeft());
		double menorDir = buscarMenor(atual.getRight());

		double menorSub = Math.min(menorEsq, menorDir);
		return Math.min(menorSub, atual.getData());
	}

	/*public void search(Node node, int data, double limiar) {
		if (node == null) {
			return;
		}
		
		double diff = data - node.getData();

		if (diff < 0) {
			search(node.getLeft(), data, limiar);
		} else if (diff > 0) {
			search(node.getRight(), data, limiar);
		} else {
			if(node.getData() >= limiar){
				List<Resultado> res = node.getResultados();
				for (Resultado r : res) {
					Logger.log(r.toString());
				}
			}
		}
	}*/

	public double BuscarposOrderRec(Node atual, String doc1, String doc2) {
		if (atual != null) {
			double resultadoEsquerda = BuscarposOrderRec(atual.getLeft(), doc1, doc2);
			if (resultadoEsquerda != 0.0) return resultadoEsquerda;

			double resultadoDireita = BuscarposOrderRec(atual.getRight(), doc1, doc2);
			if (resultadoDireita != 0.0) return resultadoDireita;

			List<Resultado> res = atual.getResultados();

			for (Resultado r : res) {
				String r1 = new java.io.File(r.getDoc1()).getName();
				String r2 = new java.io.File(r.getDoc2()).getName();
				String n1 = new java.io.File(doc1).getName();
				String n2 = new java.io.File(doc2).getName();

				boolean ordemAB = r1.equals(n1) && r2.equals(n2);
				boolean ordemBA = r1.equals(n2) && r2.equals(n1);

				if (ordemAB || ordemBA) {
					return atual.getData();
				}
			}
		}
		return 0.0;
	}

	

	public void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.getLeft());
        Logger.log("Similaridade: " + node.getData());
        for (Resultado r : node.getResultados()) {
            Logger.log("  " + r);
        }
        printInOrder(node.getRight());
    }

	public Node insert(Node node, Node parent, double data, Resultado resultado) {
		node = inserts(node, parent, (double) data, resultado); // chama o método interno
		return balance(node);
	}

	protected Node inserts(Node node, Node parent, double data, Resultado resultado) {
		if (node == null) {
			Node novo = new Node(data, parent);
			novo.getResultados().add(resultado);
			return novo;
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

    	return balance(node);
	}
	
	public Node buscar(double data) {
		return buscarRecursivo(this.root, data);
	}

	private Node buscarRecursivo(Node node, double data) {
		if (node == null) {
			return null; 
		}

		final double EPSILON = 0.0000001; 
		double diff = data - node.getData();
		
		if (diff < -EPSILON) {
			return buscarRecursivo(node.getLeft(), data);
		} else if (diff > EPSILON) {
			return buscarRecursivo(node.getRight(), data);
		} else {
			return node;
		}
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
	
	public int getDegree() {
		return getDegree(root);
	}

	private int getDegree(Node node) {
		if (node == null || node.isLeaf()) {
			return 0;
		}

		int degree = node.getDegree();
		
		if (node.hasLeftChild()) {
			degree = Math.max(degree, getDegree(node.getLeft()));
		}
		
		if (node.hasRightChild()) {
			degree = Math.max(degree, getDegree(node.getRight()));
		}
		
		return degree;
	}

	public int getHeight() {
		if (isEmpty()) {
			return -1;
		}

		return root.getHeight();
	}

	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public String toString() {
		return "AVL - isEmpty(): " + isEmpty()
				+ ", getDegree(): " + getDegree()
				+ ", getHeight(): " + getHeight()
				+ ", root => { " + root + " }";				
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
