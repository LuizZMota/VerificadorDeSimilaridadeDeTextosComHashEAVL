/*– Responsabilidade Principal: Estrutura de dados simples para armazenar o
resultado de uma compara¸c˜ao.
– Detalhes: Uma classe simples, similar a uma struct, contendo apenas atributos
para guardar os nomes dos dois documentos comparados e o valor da similaridade
calculada. Objetos desta classe ser˜ao armazenados nas listas dentro dos n´os da
´arvore AVL. */
public class Resultado {
    private String doc1;
    private String doc2;
    private double similaridade;

    public Resultado(String doc1, String doc2, double similaridade) {
        this.doc1 = doc1;
        this.doc2 = doc2;
        this.similaridade = similaridade;
    }

    
    public String getDoc1() {
        return doc1;
    }


    public void setDoc1(String doc1) {
        this.doc1 = doc1;
    }


    public String getDoc2() {
        return doc2;
    }


    public void setDoc2(String doc2) {
        this.doc2 = doc2;
    }


    public double getSimilaridade() {
        return similaridade;
    }


    public void setSimilaridade(double similaridade) {
        this.similaridade = similaridade;
    }


    @Override
    public String toString() {
        String nome1 = new java.io.File(doc1).getName();
        String nome2 = new java.io.File(doc2).getName();
        return nome1 + " <-> " + nome2 + " = " + String.format("%.2f", similaridade);
    }   
}
