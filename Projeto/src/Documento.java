/*– Responsabilidade Principal: Representar um ´unico arquivo de texto e seu
conte´udo processado.
– Detalhes: Recomenda-se que esta classe encapsule toda a l´ogica de processamento
de um ´unico arquivo. Ela deve conter atributos como o nome do arquivo e sua
pr´opria Tabela Hash interna para mapear cada palavra ´unica `a sua frequˆencia.
Seus m´etodos devem realizar a leitura do arquivo, a normaliza¸c˜ao do texto e
popular a tabela hash.*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Documento {
    
    // Nome do arquivo de texto
    private String nomeArquivo;
    
    // Tabela hash para armazenar palavras e suas frequências
    private HashTable tabelaHash;
    
    // Array de stop words em português
    private static final String[] STOP_WORDS = {
        // Artigos
        "a", "o", "as", "os", "um", "uma", "uns", "umas",
        
        // Preposições
        "de", "do", "da", "dos", "das", "em", "no", "na", "nos", "nas",
        "para", "por", "com", "sem", "sob", "sobre", "entre", "ate", "desde",
        
        // Conjunções
        "e", "ou", "mas", "pois", "que", "se", "como", "quando", "onde",
        
        // Pronomes
        "eu", "tu", "ele", "ela", "nos", "vos", "eles", "elas",
        "me", "te", "lhe", "lhes",
        
        // Verbos auxiliares comuns
        "ser", "estar", "ter", "haver", "foi", "sao", "era",
        
        // Outros
        "mais", "menos", "muito", "pouco", "todo", "toda", "todos", "todas",
        "este", "esta", "esse", "essa", "aquele", "aquela", "isso", "isto", "aquilo"
    };
    
    private static boolean isStopWord(String palavra) {
        for (String stopWord : STOP_WORDS) {
            if (stopWord.equals(palavra)) {
                return true;
            }
        }
        return false;
    }
    

    // Construtor da classe Documento
    // hashFunction Tipo de função hash a ser utilizada (HashTable.HASH_MODULO ou HASH_MULTIPLICACAO)

    public Documento(String nomeArquivo, int hashFunction) {
        this.nomeArquivo = nomeArquivo;
        // Inicializa a tabela hash com capacidade de 101 (número primo)
        this.tabelaHash = new HashTable(101, hashFunction);
        
        // Processa o documento imediatamente após a criação
        processarDocumento();
    }
    
    public Documento(String nomeArquivo) {
        this(nomeArquivo, HashTable.HASH_MODULO);
    }
    
    //  * 1. Lê o arquivo linha por linha
    //  * 2. Aplica a normalização de texto
    //  * 3. Popula a tabela hash com o vocabulário e frequências

    private void processarDocumento() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                processarLinha(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + nomeArquivo + " -> " + e.getMessage());
        }
    }
    
    //  * 1. Conversão para minúsculas
    //  * 2. Remoção de pontuação e caracteres não-alfanuméricos
    //  * 3. separação em palavras
    //  * 4. Remoção de stop words
    //  * 5. Atualização das frequências na tabela hash

    private void processarLinha(String linha) {
        // Etapa 1: Converter para minúsculas
        linha = linha.toLowerCase();
        
        // Etapa 2: Remover pontuação e caracteres não-alfanuméricos
        // Mantém apenas letras, números e espaços
        linha = linha.replaceAll("[^a-záàâãéèêíïóôõöúçñ0-9\\s]", " ");
        
        // Etapa 3:separar o texto em palavras
        String[] palavras = linha.split("\\s+");
        
        // Etapa 4 e 5: Processar cada palavra (remover stop words e atualizar frequências)
        for (String palavra : palavras) {
            // Remove espaços em branco extras
            palavra = palavra.trim();
            
            // Ignora palavras vazias ou muito curtas (menos de 2 caracteres)
            if (palavra.length() < 2) {
                continue;
            }
            
            // Etapa 4: Verifica se não é uma stop word
            if (isStopWord(palavra)) {
                continue;
            }
            
            // Etapa 5: Atualiza a frequência da palavra na tabela hash
            int frequenciaAtual = tabelaHash.get(palavra);
            
            if (frequenciaAtual == -1) {
                // Palavra ainda não existe, insere com frequência 1
                tabelaHash.put(palavra, 1);
            } else {
                // Palavra já existe, incrementa a frequência
                tabelaHash.put(palavra, frequenciaAtual + 1);
            }
        }
    }
    

    public String[] getWords() { 
        return tabelaHash.getKeys(); //retorna todas as chaves armazenadas 
    }

    public boolean isEmpty() { 
        return tabelaHash.size() == 0; 
    }

    public int getTamanhoVocabulario() {
        return tabelaHash.size();
    }
    public int getFrequencia(String key) { 
        int freq = tabelaHash.get(key);
        return Math.max(freq, 0); 
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public HashTable getTabelaHash() {
        return tabelaHash;
    }
    
    @Override
    public String toString() {
        return String.format("Documento: %s (Vocabulário: %d palavras únicas)", 
                           nomeArquivo, getTamanhoVocabulario());
    }
    
}