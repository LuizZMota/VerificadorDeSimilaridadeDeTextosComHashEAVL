/*– Responsabilidade Principal: Isolar a l´ogica de c´alculo de similaridade.
– Detalhes: Esta classe deve conter um ou mais m´etodos est´aticos ou de instˆancia
que recebem dois objetos Documento como entrada e retornam um double representando a similaridade entre eles. E aqui que a m´etrica escolhida (Cosseno, ´
Jaccard, etc.) ser´a implementada. Separar essa l´ogica facilita testes e futuras
extens˜oes (ex: adicionar novas m´etricas).*/
public class ComparadorDeDocumentos {
    
    public ComparadorDeDocumentos(){}

    public double calcularSimilaridade(Documento doc1, Documento doc2){
        if (doc1 == null || doc2 == null ) { return 0.0; }

        return 0.0;
    }

    private String[] unificarPalavras(String[] a, String[] b){
        String[] temp = new String[a.length + b.length];
        int count = 0;

        for(String s : a){
            if(!contain(a, count, s)){
                temp[count++] = s;
            }
        }

        for(String s : b){
            if(!contain(b, count, s)){
                temp[count++] = s;
            }
        }

        String[] resultado = new String[count];
        for(int i = 0;i<count;i++){
            resultado[i] = temp[i];
        }

        return resultado;
    }

    private boolean contain(String[] array, int limite, String valor){
        for (int i = 0; i<limite; i++){
            if(array[i].equals(valor)) return true;
        }
        return false;
    }
}
