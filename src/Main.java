import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {


        String file = (args.length > 0) ? args[0] : "src/female_names.txt";


        List<String> names = Files.readAllLines(Paths.get(file));


        runTest("Divisão (hashCode % size)",   new HashTableModular(), names);
        runTest("Soma ASCII (sum chars % size)", new HashTableAsciiSum(), names);


    }

    /**
     * Executa os testes de inserção e busca para uma dada tabela hash e conjunto de dados.
     * Imprime o rótulo do teste, número de colisões, tempos de inserção e busca,
     * e a distribuição das chaves na tabela.
     * @param label Rótulo descritivo para o teste.
     * @param ht Instância da tabela hash a ser testada.
     * @param data Lista de strings (nomes) a serem inseridos e buscados.
     */
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