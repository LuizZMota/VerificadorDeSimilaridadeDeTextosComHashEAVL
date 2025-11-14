import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        
        Logger.init("resultado.txt");

        if (args.length < 3) {
            Logger.log("Uso: java Main <diretorio> <limiar> <modo> [args_opcionais]");
            Logger.close();
            return;
        }

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2];

        AVL avlTree = new AVL(null);
        
        List<Documento> documentosLidos = new ArrayList<>();

        File pastaDocumentos = new File(diretorio);

        File[] arquivos = pastaDocumentos.listFiles();

        if (arquivos == null || arquivos.length == 0) {
            Logger.log("Erro: Não foram encontrados arquivos .txt no diretório: " + diretorio);
            Logger.close();
            return; 
        }

        for (File arquivo : arquivos) {
            Documento doc = new Documento(arquivo.getPath());
            documentosLidos.add(doc);         
        }

        ComparadorDeDocumentos comparador = new ComparadorDeDocumentos("Cosseno");
        int count = 0;
        for (int i=0;i<documentosLidos.size();i++){
            for (int j = i+1; j<documentosLidos.size();j++){
                Documento d1 = documentosLidos.get(i);
                Documento d2 = documentosLidos.get(j);
                
                double similaridade = comparador.calcularCosseno(d1, d2);
                Resultado res = new Resultado(d1.getNomeArquivo(), d2.getNomeArquivo(), similaridade);
                avlTree.setRoot(avlTree.insert(avlTree.getRoot(), null, similaridade, res));
                count++;
            }
        }
        
        if (modo.equalsIgnoreCase("lista")) {
            Logger.log("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
            Logger.log("\nTotal de documentos processados: " + documentosLidos.size());
            Logger.log("Total de pares comparados: " + (count));
            Logger.log("Função hash utilizada: " + documentosLidos.get(0).getTabelaHash().getHashFunctionName());
            Logger.log("Métrica de similaridade: " + comparador.getMetrica() + "\n");
            Logger.log("Pares com similaridade >= " + limiar);         
            Logger.log("-------------------------------------------------");
            avlTree.maiorSimilaridade(avlTree.getRoot(), limiar);
            Logger.log("\nPares com menor similaridade: ");
            Logger.log("-------------------------------------------------");
            avlTree.menorSimilaridade(avlTree.getRoot());
        }

        if (modo.equalsIgnoreCase("topK")) {
            if (args.length < 4) {
                Logger.log("Erro: Use java Main <dir> <limiar> topK <K>");
                Logger.close();
                return;
            }

            int K = Integer.parseInt(args[3]);
            List<Resultado> resultadosOrdenados = new ArrayList<>();

            avlTree.topSimilaridades(avlTree.getRoot(), resultadosOrdenados, limiar);

            resultadosOrdenados.sort((a, b) -> Double.compare(b.getSimilaridade(), a.getSimilaridade()));

            Logger.log("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
            Logger.log("\nTop " + K + " pares mais semelhantes:");
            Logger.log("---------------------------------");

            for (int i = 0; i < Math.min(K, resultadosOrdenados.size()); i++) {
                Logger.log(resultadosOrdenados.get(i).toString());
            }
                
        }

        if (modo.equalsIgnoreCase("busca")) {
            if (args.length < 5) {
                Logger.log("Erro: Faltam argumentos para o modo 'busca'. Use: java Main <dir> <limiar> busca <arq1> <arq2>");
                Logger.close();
                return; 
            }
            String arquivo1 = args[3];
            String arquivo2 = args[4];

            double resp = avlTree.buscarDocs(avlTree.getRoot(), arquivo1, arquivo2);

            
            Logger.log("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
            Logger.log("\nComparando: " + arquivo1 + " <-> " + arquivo2);            
            Logger.log(String.format("Similaridade calculada: %.2f", resp));
            Logger.log("Métrica utilizada: " + comparador.getMetrica() + "\n");
        }

        gerarAnaliseComparativa(diretorio);
        Logger.close();
    }

    private static void gerarAnaliseComparativa(String diretorio) {
        Logger.log("Processando documentos com AMBAS as funções hash para comparação...\n");
        
        File pastaDocumentos = new File(diretorio);
        File[] arquivos = pastaDocumentos.listFiles();
        
        if (arquivos == null || arquivos.length == 0) {
            Logger.log("Erro: Não há arquivos para análise.");
            return;
        }
        
        // Processar com função MÓDULO
        List<Documento> docsModulo = new ArrayList<>();
        for (File arquivo : arquivos) {
            Documento doc = new Documento(arquivo.getPath(), HashTable.HASH_MODULO);
            docsModulo.add(doc);
        }
        
        // Processar com função MULTIPLICAÇÃO
        List<Documento> docsMultiplicacao = new ArrayList<>();
        for (File arquivo : arquivos) {
            Documento doc = new Documento(arquivo.getPath(), HashTable.HASH_MULTIPLICACAO);
            docsMultiplicacao.add(doc);
        }
        
        // Coletar estatísticas agregadas
        long colisoesMod = 0, colisoesMulti = 0;
        long insercoesMod = 0, insercoesMulti = 0;
        int maxChainMod = 0, maxChainMulti = 0;
        int totalEmptyMod = 0, totalEmptyMulti = 0;
        double somaLoadFactorMod = 0, somaLoadFactorMulti = 0;
        
        for (Documento doc : docsModulo) {
            HashTable h = doc.getTabelaHash();
            colisoesMod += h.getCollisionCount();
            insercoesMod += h.getInsertionCount();
            maxChainMod = Math.max(maxChainMod, h.getMaxChainLength());
            totalEmptyMod += h.getEmptyBuckets();
            somaLoadFactorMod += h.getLoadFactor();
        }
        
        for (Documento doc : docsMultiplicacao) {
            HashTable h = doc.getTabelaHash();
            colisoesMulti += h.getCollisionCount();
            insercoesMulti += h.getInsertionCount();
            maxChainMulti = Math.max(maxChainMulti, h.getMaxChainLength());
            totalEmptyMulti += h.getEmptyBuckets();
            somaLoadFactorMulti += h.getLoadFactor();
        }
        
        double taxaColisaoMod = (double) colisoesMod / insercoesMod * 100;
        double taxaColisaoMulti = (double) colisoesMulti / insercoesMulti * 100;
        double mediaLoadFactorMod = somaLoadFactorMod / docsModulo.size();
        double mediaLoadFactorMulti = somaLoadFactorMulti / docsMultiplicacao.size();
        
        // Exibir tabela comparativa
        Logger.log("╔════════════════════════════════════════════════════════════════════════════╗");
        Logger.log("║                    COMPARAÇÃO DE FUNÇÕES HASH                              ║");
        Logger.log("╠════════════════════════════════════╦═══════════════╦═══════════════════════╣");
        Logger.log("║ MÉTRICA                            ║    MÓDULO     ║    MULTIPLICAÇÃO      ║");
        Logger.log("╠════════════════════════════════════╬═══════════════╬═══════════════════════╣");
        Logger.log(String.format("║ Total de Inserções                 ║ %13d ║ %21d ║", 
            insercoesMod, insercoesMulti));
        Logger.log(String.format("║ Total de Colisões                  ║ %13d ║ %21d ║", 
            colisoesMod, colisoesMulti));
        Logger.log(String.format("║ Taxa de Colisão                    ║ %12.2f%% ║ %20.2f%% ║", 
            taxaColisaoMod, taxaColisaoMulti));
        Logger.log("╠════════════════════════════════════╬═══════════════╬═══════════════════════╣");
        Logger.log(String.format("║ Maior Cadeia (pior caso)           ║ %13d ║ %21d ║", 
            maxChainMod, maxChainMulti));
        Logger.log(String.format("║ Total de Buckets Vazios            ║ %13d ║ %21d ║", 
            totalEmptyMod, totalEmptyMulti));
        Logger.log(String.format("║ Fator de Carga Médio               ║ %13.4f ║ %21.4f ║", 
            mediaLoadFactorMod, mediaLoadFactorMulti));
        Logger.log("╚════════════════════════════════════╩═══════════════╩═══════════════════════╝");
        Logger.log("");
    }
}
