import java.util.ArrayList;
import java.util.List;

public abstract class HashTable {
    protected int size;
    protected List<List<String>> buckets; // Mesmo tamanho do size
    protected int collisions = 0;
    protected int count = 0;   // total de chaves inseridas
    protected final double loadFactor = 0.75; // Fator

    public HashTable() {
        this(16);
    }

    // Construtor customizado
    public HashTable(int initialCapacity) {
        this.size = initialCapacity;
        this.buckets = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    public void insert(String key) {
        // Redimensiona
        if ((double)(count + 1) / size > loadFactor) {
            resize();
        }

        // 2) hash + chaining
        int idx = hash(key);
        List<String> bucket = buckets.get(idx);
        if (!bucket.isEmpty()) {
            collisions++;
        }
        bucket.add(key);
        count++;
    }

    public boolean search(String key) {
        int idx = hash(key);
        return buckets.get(idx).contains(key);
    }

    public int getCollisions() {
        return collisions;
    }

    public int[] getDistribution() {
        int[] dist = new int[size];
        for (int i = 0; i < size; i++) {
            dist[i] = buckets.get(i).size();
        }
        return dist;
    }

    // Dobrar capacidade e re-hash de todas as chaves
    private void resize() {
        int newSize = size * 2;
        List<List<String>> newBuckets = new ArrayList<>(newSize);
        for (int i = 0; i < newSize; i++) {
            newBuckets.add(new ArrayList<>());
        }

        // Ajusta size antes do re-hash para usar a mesma função hash()
        this.size = newSize;
        // Re-insere tudo nos novos buckets (mantém collisions e count originais)
        for (List<String> bucket : buckets) {
            for (String key : bucket) {
                int idx = hash(key);
                newBuckets.get(idx).add(key);
            }
        }
        this.buckets = newBuckets;
    }

    // Cada subclasse define sua função hash
    protected abstract int hash(String key);
}