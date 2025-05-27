public class HashTableModular extends HashTable {
    public HashTableModular() { super(); }
    public HashTableModular(int size) { super(size); }

    @Override
    protected int hash(String key) {
        return Math.abs(key.hashCode()) % size;
    }
}
//