// src/Main.java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // usa args[0] se passado, senão "src/female_names.txt"
        // ALTERAÇÃO AQUI: Adicionado "src/" ao caminho padrão do arquivo
        String file = (args.length > 0) ? args[0] : "src/female_names.txt";

        // lê todos os nomes do arquivo (espera ~5000 linhas)
        List<String> names = Files.readAllLines(Paths.get(file));

        // ... restante do seu código ...

        runTest("Divisão (hashCode % size)",   new HashTableModular(), names);
        runTest("Soma ASCII (sum chars % size)", new HashTableAsciiSum(), names);
    }

    private static void runTest(String label, HashTable ht, List<String> data) {
        long t0 = System.nanoTime();
        data.forEach(ht::insert);
        long t1 = System.nanoTime();
        data.forEach(ht::search);
        long t2 = System.nanoTime();

        System.out.println(">> " + label);
        System.out.println("Colisões: " + ht.getCollisions());
        System.out.printf("Tempo inserção: %.3f ms%n", (t1 - t0) / 1e6);
        System.out.printf("Tempo busca:     %.3f ms%n", (t2 - t1) / 1e6);
        System.out.println("Distribuição: " + Arrays.toString(ht.getDistribution()));
        System.out.println();
    }
}