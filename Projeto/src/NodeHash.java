
// Classe NodeHash - Representa um nó na lista encadeada da tabela hash

public class NodeHash {
    
    // Chave (palavra do documento)
    String key;
    
    // Valor (frequência da palavra)
    int value;
    
    // Referência para o próximo nó na lista encadeada
    NodeHash next;
    
    public NodeHash(String key, int value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}