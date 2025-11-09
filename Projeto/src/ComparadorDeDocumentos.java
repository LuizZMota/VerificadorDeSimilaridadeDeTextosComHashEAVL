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

        if (palavras1 == null) palavras1 = new String[0];
        if (palavras2 == null) palavras2 = new String[0];

        String[] todas = unificarPalavras(palavras1, palavras2);

        double produtoEscalar = 0.0;
        double somaQuadrado1 = 0.0;
        double somaQuadrado2 = 0.0;

        for (int i = 0; i < todas.length; i++) {
            String palavra = todas[i];
            if (palavra == null) continue;

            int f1 = doc1.getFrequencia(palavra);
            int f2 = doc2.getFrequencia(palavra);

            produtoEscalar += ((double) f1) * f2;
            somaQuadrado1 += ((double) f1) * f1;
            somaQuadrado2 += ((double) f2) * f2;
        }
        
        double magnitude1 = Math.sqrt(somaQuadrado1);
        double magnitude2 = Math.sqrt(somaQuadrado2);

        if (magnitude1 == 0.0 || magnitude2 == 0.0){
            return 0.0;
        }

        return produtoEscalar / (magnitude1 * magnitude2);
    }

    private String[] unificarPalavras(String[] a, String[] b){
        int max = (a == null ? 0 : a.length) + (b == null ? 0 : b.length);
        String[] temp = new String[max];
        int count = 0;

        for (int i = 0; i < (a == null ? 0 : a.length); i++) {
            String s = a[i];
            if (s == null) continue;
            s = s.trim();
            if (s.isEmpty()) continue;
            if (!contain(temp, count, s)) {
                temp[count++] = s;
            }
        }

        // adiciona únicas de b
        for (int i = 0; i < (b == null ? 0 : b.length); i++) {
            String s = b[i];
            if (s == null) continue;
            s = s.trim();
            if (s.isEmpty()) continue;
            if (!contain(temp, count, s)) {
                temp[count++] = s;
            }
        }

        String[] resultado = new String[count];
        for (int i = 0; i < count; i++) resultado[i] = temp[i];
        return resultado;
    }

    private boolean contain(String[] array, int limite, String valor){
        if (valor == null) return false;
        for (int i = 0; i < limite; i++){
            if (array[i] != null && array[i].equals(valor)) return true;
        }
        return false;
    }
}
