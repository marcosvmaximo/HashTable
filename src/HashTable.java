public abstract class HashTable {
    protected int size;
    protected Node[] buckets;
    protected int collisions = 0;
    protected int count = 0;
    protected final double loadFactor = 0.75;

    protected static class Node {
        String key;
        Node next;

        Node(String key) {
            this.key = key;
            this.next = null;
        }
    }

    public HashTable() {
        this(16);
    }

    public HashTable(int initialCapacity) {
        this.size = initialCapacity;
        this.buckets = new Node[size];
    }

    public void insert(String key) {
        if ((double)(count + 1) / size > loadFactor) {
            resize();
        }
        int idx = hash(key);
        Node newNode = new Node(key);

        if (buckets[idx] == null) {
            buckets[idx] = newNode;
        } else {
            collisions++;
            newNode.next = buckets[idx];
            buckets[idx] = newNode;
        }
        count++;
    }

    public boolean search(String key) {
        int idx = hash(key);
        Node current = buckets[idx];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int getCollisions() {
        return collisions;
    }

    public int[] getDistribution() {
        int[] dist = new int[size];
        for (int i = 0; i < size; i++) {
            Node current = buckets[i];
            int bucketSize = 0;
            while (current != null) {
                bucketSize++;
                current = current.next;
            }
            dist[i] = bucketSize;
        }
        return dist;
    }

    private void resize() {
        Node[] oldBuckets = this.buckets;
        int oldSize = this.size;

        this.size = oldSize * 2;
        this.buckets = new Node[this.size];
        this.count = 0;

        for (int i = 0; i < oldSize; i++) {
            Node current = oldBuckets[i];
            while (current != null) {
                String keyToRehash = current.key;
                int newIdx = hash(keyToRehash);

                Node newNode = new Node(keyToRehash);
                if (this.buckets[newIdx] == null) {
                    this.buckets[newIdx] = newNode;
                } else {
                    newNode.next = this.buckets[newIdx];
                    this.buckets[newIdx] = newNode;
                }
                this.count++;
                current = current.next;
            }
        }
    }

    protected abstract int hash(String key);
}