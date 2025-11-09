/*– Responsabilidade Principal: Isolar a l´ogica de c´alculo de similaridade.
– Detalhes: Esta classe deve conter um ou mais m´etodos est´aticos ou de instˆancia
que recebem dois objetos Documento como entrada e retornam um double representando a similaridade entre eles. E aqui que a m´etrica escolhida (Cosseno, ´
Jaccard, etc.) ser´a implementada. Separar essa l´ogica facilita testes e futuras
extens˜oes (ex: adicionar novas m´etricas).*/
public class ComparadorDeDocumentos {
    String metrica;
    public ComparadorDeDocumentos(String metrica){
        this.metrica = metrica;
    }
    
    public String getMetrica() {
            return metrica;
    }

    public double calcularCosseno(Documento doc1, Documento doc2){
        if (doc1 == null || doc2 == null ) { return 0.0; }
        String[] palavras1 = doc1.getWords();
        String[] palavras2 = doc2.getWords();
        String[] todas = unificarPalavras(palavras1, palavras2);

        double produtoEscalar = 0.0;
        double somaQuadrado = 0.0;
        double somaQuadrado2 = 0.0;

        for(String palavra : todas){
            int f1 = doc1.getFrequencia(palavra);
            int f2 = doc2.getFrequencia(palavra);

            produtoEscalar += (f1 * f2);
            somaQuadrado += (f1 * f1);
            somaQuadrado2 += (f2 * f2);
        }
        
        double magnitude1 = Math.sqrt(somaQuadrado);
        double magnitude2 = Math.sqrt(somaQuadrado2);

        if (magnitude1 == 0 || magnitude2 == 0){
            return 0.0;
        }

        return produtoEscalar / (magnitude1 * magnitude2);
    }

    private String[] unificarPalavras(String[] a, String[] b){
        String[] temp = new String[a.length + b.length];
        int count = 0;

        for(String s : a){
            if(!containsInTemp(temp, count, s)){
                temp[count++] = s;
            }
        }

        for(String s : b){
            if(!containsInTemp(temp, count, s)){
                temp[count++] = s;
            }
        }

        String[] resultado = new String[count];
        for(int i = 0;i<count;i++){
            resultado[i] = temp[i];
        }

        return resultado;
    }

    private boolean containsInTemp(String[] temp, int limite, String valor) {
        for (int i = 0; i < limite; i++) {
            if (temp[i] != null && temp[i].equals(valor)) {
                return true;
            }
        }
        return false;
    }

}
