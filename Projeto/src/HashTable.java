/*– Responsabilidade Principal: Implementação manual de uma tabela hash genérica.
– Detalhes: Deve ser uma implementaçãao do zero, sem usar o HashMap do Java.
A classe deve gerenciar um array interno e as duas fun¸c˜oes de dispers˜ao
(hash) criadas pelo aluno. Métodos essenciais incluem put(chave, valor) e
get(chave). O tratamento de colisões é uma parte crítica desta implementação.*/
public class HashTable {
    
    // Array de buckets (cada posição é uma lista encadeada)
    private NodeHash[] buckets;
    
    // Capacidade inicial da tabela
    private int capacity;
    
    // Número de elementos armazenados
    private int size;
    
    // Tipo de função hash a ser utilizada
    private int hashFunction;
    
    // Contador de colisões
    private int collisionCount;
    
    // Constantes para identificar as funções hash
    public static final int HASH_MODULO = 1;
    public static final int HASH_MULTIPLICACAO = 2;
    
    // Constante para o método de multiplicação (valor sugerido por Knuth)
    private static final double A = 0.6180339887; // (√5 - 1) / 2
    
    
    // Construtor padrão - usa função hash por módulo e capacidade inicial de 101
    public HashTable() {
        this(101, HASH_MODULO);
    }
    
    public HashTable(int capacity) {
        this(capacity, HASH_MODULO);
    }
    

    // Construtor completo que permite especificar capacidade e função hash
    public HashTable(int capacity, int hashFunction) {
        this.capacity = capacity;
        this.buckets = new NodeHash[capacity];
        this.size = 0;
        this.hashFunction = hashFunction;
        this.collisionCount = 0;
    }
    

    // Calcula o índice do bucket usando a função hash selecionada
    private int hash(String key) {
        if (hashFunction == HASH_MULTIPLICACAO) {
            return hashMultiplicacao(key);
        } else {
            return hashModulo(key);
        }
    }
    
    //Função Hash 1: Método da Divisão (Módulo)
    private int hashModulo(String key) {
        int hashCode = key.hashCode();
        // Garante que o valor seja positivo
        int index = Math.abs(hashCode) % capacity;
        return index;
    }
    
    //Função Hash 2: Método da Multiplicação
    //Fórmula: h(k) = ⌊m × ((k × A) mod 1)⌋
    private int hashMultiplicacao(String key) {
        int hashCode = Math.abs(key.hashCode());
        // Método da multiplicação: h(k) = floor(m * ((k * A) mod 1))
        double produto = hashCode * A;
        double fracaoPura = produto - Math.floor(produto);
        int index = (int) (capacity * fracaoPura);
        return index;
    }
    
    // Insere ou atualiza um par chave-valor na tabela hash
    // Se a chave já existe, atualiza o valor. Caso contrário, insere um novo nó.
    public void put(String key, int value) {
        if (key == null) {
            throw new IllegalArgumentException("Chave não pode ser nula");
        }
        
        int index = hash(key);
        NodeHash current = buckets[index];
        
        // Verifica se a chave já existe no bucket
        while (current != null) {
            if (current.key.equals(key)) {
                // Atualiza o valor existente
                current.value = value;
                return;
            }
            current = current.next;
        }
        
        // Chave não existe: insere novo nó no início do bucket
        NodeHash newNode = new NodeHash(key, value);
        
        // Se o bucket já tem elementos, houve uma colisão
        if (buckets[index] != null) {
            collisionCount++;
        }
        
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++; // Incrementa size SOMENTE quando insere nova chave
    }
    
    // Recupera o valor associado a uma chave
    public int get(String key) {
        if (key == null) {
            return -1;
        }
        
        int index = hash(key);
        NodeHash current = buckets[index];
        
        // Percorre a lista encadeada do bucket
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        
        // Chave não encontrada
        return -1;
    }
    
    public int size() {
        return size;
    }
    
    public int getCapacity() {
        return capacity;
    }


    public String[] getKeys(){
        String[] keys = new String[size];
        int index = 0;

        for (int i =0;i<capacity;i++){
            NodeHash current = buckets[i];
            while(current != null){
                keys[index++] = current.key;
                current = current.next;
            }
        }

        return keys;
    }
    public String getHashFunctionName() {
        return hashFunction == HASH_MULTIPLICACAO ? "hashMultiplicativo" : "hashModulo";
    }
    
    public int getCollisionCount() {
        return collisionCount;
    }
    

    // Retorna a distribuição das chaves por bucket
    public int[] getDistribuicao() {
        int[] distribuicao = new int[capacity];
        
        for (int i = 0; i < capacity; i++) {
            int count = 0;
            NodeHash current = buckets[i];
            while (current != null) {
                count++;
                current = current.next;
            }
            distribuicao[i] = count;
        }
        
        return distribuicao;
    }
}