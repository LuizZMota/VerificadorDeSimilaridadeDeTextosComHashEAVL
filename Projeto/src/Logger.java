import java.io.*;

public class Logger {
    private static PrintStream fileOut;

    public static void init(String fileName) {
        try {
            fileOut = new PrintStream(new FileOutputStream(fileName));
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo de log: " + e.getMessage());
        }
    }

    public static void log(String msg) {
        System.out.println(msg);
        if (fileOut != null) fileOut.println(msg);
    }

    public static void close() {
        if (fileOut != null) {
            fileOut.close();
        }
    }
}

