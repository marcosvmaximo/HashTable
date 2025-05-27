// src/Main.java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Usa args[0] se passado como argumento na linha de comando,
        // senão, usa "src/female_names.txt" como padrão.
        String file = (args.length > 0) ? args[0] : "src/female_names.txt";

        // Lê todos os nomes do arquivo.
        List<String> names = Files.readAllLines(Paths.get(file));

        // Testa as duas implementações da tabela hash.
        // Ambas são inicializadas com a capacidade padrão de 16,
        // conforme definido no construtor de HashTable.
        // Isso garante que o mecanismo de redimensionamento (resize) seja testado
        // ao inserir 5000 nomes.
        runTest("Divisão (hashCode % size)",   new HashTableModular(), names);
        runTest("Soma ASCII (sum chars % size)", new HashTableAsciiSum(), names);

        // Se preferir inicializar explicitamente com 32 (o máximo permitido no TDE):
        // int initialCapacity = 32;
        // runTest("Divisão (hashCode % size)",   new HashTableModular(initialCapacity), names);
        // runTest("Soma ASCII (sum chars % size)", new HashTableAsciiSum(initialCapacity), names);
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
        // Mede o tempo de inserção
        long t0 = System.nanoTime();
        data.forEach(ht::insert); // Insere todos os nomes na tabela hash
        long t1 = System.nanoTime();

        // Mede o tempo de busca
        data.forEach(ht::search); // Busca todos os nomes na tabela hash
        long t2 = System.nanoTime();

        // Imprime os resultados
        System.out.println(">> " + label);
        System.out.println("Colisões: " + ht.getCollisions());
        System.out.printf("Tempo inserção: %.3f ms%n", (t1 - t0) / 1e6); // Converte nanossegundos para milissegundos
        System.out.printf("Tempo busca:     %.3f ms%n", (t2 - t1) / 1e6); // Converte nanossegundos para milissegundos
        System.out.println("Distribuição: " + Arrays.toString(ht.getDistribution()));
        System.out.println(); // Linha em branco para separar os resultados dos testes
    }
}