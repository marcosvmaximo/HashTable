// import java.util.ArrayList; // Removido pois não usaremos mais ArrayList para a estrutura principal
// import java.util.List;    // Removido

public abstract class HashTable {
    protected int size;
    protected Node[] buckets; // Array de nós (cabeças das listas encadeadas)
    protected int collisions = 0;
    protected int count = 0;   // total de chaves inseridas
    protected final double loadFactor = 0.75; // Fator de Carga

    // Nó para a lista encadeada (chaining)
    protected static class Node { // Classe Node está correta
        String key;
        Node next; // Campo next pertence a Node

        Node(String key) {
            this.key = key;
            this.next = null; // 'this' aqui se refere à instância de Node, 'this.next' está ok
        }
    }

    public HashTable() {
        this(16); // Capacidade inicial padrão
    }

    // Construtor customizado
    public HashTable(int initialCapacity) {
        this.size = initialCapacity;
        this.buckets = new Node[size]; // 'this.buckets' e 'this.size' estão corretos
    }

    public void insert(String key) {
        if ((double)(count + 1) / size > loadFactor) {
            resize();
        }
        int idx = hash(key);
        Node newNode = new Node(key); // newNode é do tipo Node

        if (buckets[idx] == null) { // Acesso a buckets[idx] está correto
            buckets[idx] = newNode;
        } else {
            collisions++;
            newNode.next = buckets[idx]; // newNode.next e buckets[idx] (que é um Node) estão corretos
            buckets[idx] = newNode;
        }
        count++;
    }

    public boolean search(String key) {
        int idx = hash(key);
        Node current = buckets[idx]; // current é do tipo Node
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next; // current.next está correto
        }
        return false;
    }

    public int getCollisions() {
        return collisions;
    }

    public int[] getDistribution() {
        int[] dist = new int[size]; // size está correto
        for (int i = 0; i < size; i++) {
            Node current = buckets[i]; // current é do tipo Node
            int bucketSize = 0;
            while (current != null) {
                bucketSize++;
                current = current.next; // current.next está correto
            }
            dist[i] = bucketSize;
        }
        return dist;
    }

    private void resize() {
        Node[] oldBuckets = this.buckets; // this.buckets está correto
        int oldSize = this.size; // this.size está correto

        this.size = oldSize * 2;
        this.buckets = new Node[this.size]; // this.buckets e this.size estão corretos
        this.count = 0;

        for (int i = 0; i < oldSize; i++) {
            Node current = oldBuckets[i]; // current é Node
            while (current != null) {
                String keyToRehash = current.key;
                int newIdx = hash(keyToRehash);

                Node newNode = new Node(keyToRehash); // newNode é Node
                if (this.buckets[newIdx] == null) { // this.buckets está correto
                    this.buckets[newIdx] = newNode;
                } else {
                    newNode.next = this.buckets[newIdx]; // newNode.next e this.buckets[newIdx] (Node) estão corretos
                    this.buckets[newIdx] = newNode;
                }
                this.count++;
                current = current.next; // current.next está correto
            }
        }
    }

    protected abstract int hash(String key);
}