public class HashTableAsciiSum extends HashTable {
    public HashTableAsciiSum() { super(); }
    public HashTableAsciiSum(int size) { super(size); }

    @Override
    protected int hash(String key) {
        int sum = 0;
        for (char c : key.toCharArray()) sum += c;
        return sum % size;
    }
}