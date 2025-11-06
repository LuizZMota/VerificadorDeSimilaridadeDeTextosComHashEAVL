/*– Responsabilidade Principal: Isolar a l´ogica de c´alculo de similaridade.
– Detalhes: Esta classe deve conter um ou mais m´etodos est´aticos ou de instˆancia
que recebem dois objetos Documento como entrada e retornam um double representando a similaridade entre eles. E aqui que a m´etrica escolhida (Cosseno, ´
Jaccard, etc.) ser´a implementada. Separar essa l´ogica facilita testes e futuras
extens˜oes (ex: adicionar novas m´etricas).*/
public class ComparadorDeDocumentos {
    public Documento doc1;
    public Documento doc2;

    public ComparadorDeDocumentos(){
    }

    public double calcularSimilaridade(Documento doc1, Documento doc2){
        if (doc1 == null || doc2 == null ) { return 0.0; }
        
        return 0.0;
    }
}
