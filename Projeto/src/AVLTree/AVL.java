package AVLTree;

public class AVL extends BST{

    public AVL(BTNode root){
        super(root);
    }

    public void insert(double data) {
        setRoot(insertRec(getRoot(), data));
    }

    
    public BTNode insertRec(BTNode no, double data){
        if (no == null) {
            return new BTNode(data);
        }

        if(no.getData() > data){
            no.setLeft(insertRec(no.getLeft(), data));
            if (no.getLeft() != null) no.getLeft().setParent(no);
        }else{
            no.setRight(insertRec(no.getRight(), data));
            if (no.getRight() != null) no.getRight().setParent(no);
        }

        updateBalance(no);

        if(no.getBalanceFactor() < -1 || no.getBalanceFactor() > 1){
            no = balanceHelper(no);
        }

        return no;
    }

    public void delete(double data){
        setRoot(delete(getRoot(), data));
    }

    
    public BTNode delete(BTNode no, double data){
        if(no == null){
            return null;
        }

        if(no.getData() < data){
            no.setRight(delete(no.getRight(), data));
        }else if(no.getData() > data){
            no.setLeft(delete(no.getLeft(), data));
        }else{
            if(no.isLeaf()){
                return null;
            }else if(no.getDegree() == 1){
                if(no.getLeft() != null){
                    return no.getLeft();
                }else{
                    return no.getRight();
                }
            }else {
                BTNode temp = findMin(no.getRight());
                no.setData(temp.getData());
                no.setRight(delete(no.getRight(), temp.getData()));
            }
        }

        updateBalance(no);


        if(no.getBalanceFactor() < -1 || no.getBalanceFactor() > 1){
            no = balanceHelper(no);
        }

        return no;
    }

    public BTNode balanceHelper(BTNode no){
        if(no.getBalanceFactor() > 1){ //Desbalanceamento para o lado esquerdo
            if(no.getLeft() != null && no.getLeft().getBalanceFactor() >= 0){
                return rotateRight(no);
            }else if(no.getLeft() != null && no.getLeft().getBalanceFactor() < 0){
                return rotateLeftRight(no);
            }
        }

        if(no.getBalanceFactor() < -1){ //Desbalanceamento para o lado direito
            if(no.getRight() != null && no.getRight().getBalanceFactor() <= 0){
                return rotateLeft(no);
            }else if(no.getRight() != null && no.getRight().getBalanceFactor() > 0){
                return rotateRightLeft(no);
            }
        }

        return no;
    }

    //O parametro "no" é a raiz da subarvore que precisa ser balanceada
    public BTNode rotateRight(BTNode no){
        System.out.println("  [ROTAÇÃO SIMPLES À DIREITA no nó " + no.getData() + "]");
        boolean ehraiz = no.isRoot();

        BTNode esq = no.getLeft();
        BTNode temp = esq.getRight();

        no.setLeft(temp);
        if(temp != null) temp.setParent(no);

        esq.setRight(no);
        no.setParent(esq);

        updateBalance(no);
        updateBalance(esq);

        if(ehraiz){
            setRoot(esq);
        }

        return esq;
    }

    public BTNode rotateLeft(BTNode no){
        System.out.println("  [ROTAÇÃO SIMPLES À ESQUERDA no nó " + no.getData() + "]");
        boolean ehraiz = no.isRoot();

        BTNode dir = no.getRight();
        BTNode temp = dir.getLeft();

        no.setRight(temp);
        if(temp != null) temp.setParent(no);

        dir.setLeft(no);
        no.setParent(dir);

        updateBalance(no);
        updateBalance(dir);
        
        if(ehraiz){
            setRoot(dir);
        }
        return dir;
    }

    public BTNode rotateLeftRight(BTNode no){
        System.out.println("  [ROTAÇÃO DUPLA: ESQUERDA-DIREITA no nó " + no.getData() + "]");
        no.setLeft(rotateLeft(no.getLeft()));
        return rotateRight(no);
    }

    public BTNode rotateRightLeft(BTNode no){
        System.out.println("  [ROTAÇÃO DUPLA: DIREITA-ESQUERDA no nó " + no.getData() + "]");
        no.setRight(rotateRight(no.getRight()));
        return rotateLeft(no);
    }

    public int getHeight(BTNode no){
        if(no == null){
            return 0;
        }

        return 1 + Math.max(getHeight(no.getRight()), getHeight(no.getLeft()));
    }

    public int getBalance(BTNode no){ //Pega o fator de balanceamento
        return getHeight(no.getLeft()) - getHeight(no.getRight());
    }

    public void updateBalance(BTNode no){ //Atualiza o fator de Balanceamento
        no.setBalanceFactor(getBalance(no));
    }

    public void detalhesAVL(BTNode node) {
        if (node != null) {
            System.out.println("  Nó " + node.getData() + ": Pai=" + 
                (node.getParent() != null ? node.getParent().getData() : "null") +
                ", Esq=" + (node.getLeft() != null ? node.getLeft().getData() : "null") + 
                ", Dir=" + (node.getRight() != null ? node.getRight().getData() : "null") + 
                ", FB=" + node.getBalanceFactor());
            
            detalhesAVL(node.getLeft());
            detalhesAVL(node.getRight());
        }
    }
}