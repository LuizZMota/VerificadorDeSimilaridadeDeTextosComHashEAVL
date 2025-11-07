import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;


public class Main {


    public static void main(String[] args) {
        
        if (args.length < 3) {
            System.out.println("Uso: java Main <diretorio> <limiar> <modo> [args_opcionais]");
            return;
        }

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2];

        AVL avlTree = new AVL(null);
        
        List<Documento> documentosLidos = new ArrayList<>();

        File pastaDocumentos = new File(diretorio);

        FilenameFilter filtroTxt = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               return name.toLowerCase().endsWith(".txt");
            }
        };

        File[] arquivos = pastaDocumentos.listFiles(filtroTxt);

        if (arquivos == null || arquivos.length == 0) {
            System.err.println("Erro: Não foram encontrados arquivos .txt no diretório: " + diretorio);
            return; 
        }

        for (File arquivo : arquivos) {
            String caminhoCompleto = arquivo.getAbsolutePath(); 
            
            String nomeDoArquivo = arquivo.getName();

            Documento novoDocumento = new Documento(caminhoCompleto);
            
            documentosLidos.add(novoDocumento);
            System.out.println("Processando: " + nomeDoArquivo);
        }

        ComparadorDeDocumentos comparador = new ComparadorDeDocumentos("Cosseno");
        for (int i=0;i<documentosLidos.size();i++){
            for (int j = 0; j<documentosLidos.size();j++){
                Documento d1 = documentosLidos.get(i);
                Documento d2 = documentosLidos.get(j);

                double similaridade = comparador.calcularCosseno(d1, d2);
                Resultado res = new Resultado(d1.getNomeArquivo(), d2.getNomeArquivo(), similaridade);
                avlTree.setRoot(avlTree.insert(avlTree.getRoot(), null, similaridade, res));
            }
        }

        System.out.println("Total de documentos processados: " + documentosLidos.size());

        if (modo.equalsIgnoreCase("lista")) {
          
            
        }

        if (modo.equalsIgnoreCase("topK")) {
            
            
        }

        if (modo.equalsIgnoreCase("busca")) {
            if (args.length < 5) {
                System.out.println("Erro: Faltam argumentos para o modo 'busca'. Use: java Main <dir> <limiar> busca <arq1> <arq2>");
                return; 
            }
            String arquivo1 = args[3];
            String arquivo2 = args[4];

            
            
        }

    }
}
