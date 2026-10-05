// Hand-built growable int array (java.util is not allowed inside the engine)
public class IntList {
    private int[] data = new int[16];
    private int size = 0;
    public void add(int v) {
        if (size == data.length) {
            int[] bigger = new int[size * 2];
            System.arraycopy(data, 0, bigger, 0, size);
            data = bigger;
        }
        data[size++] = v;
    }
    public int get(int i) { return data[i]; }
    public int size() { return size; }
    public int[] toArray() {
        int[] r = new int[size];
        System.arraycopy(data, 0, r, 0, size);
        return r;
    }
}
