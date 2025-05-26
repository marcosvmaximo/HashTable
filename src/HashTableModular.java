public class HashTableModular extends HashTable {
    public HashTableModular() { super(); }
    public HashTableModular(int size) { super(size); } // 'size' aqui é um parâmetro, não o campo da classe

    @Override
    protected int hash(String key) {
        return Math.abs(key.hashCode()) % size; // 'size' aqui DEVE ser o campo herdado de HashTable
    }
}