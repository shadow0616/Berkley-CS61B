public class ArrayDeque<Tifa> {
    private Tifa[] items;
    private int nextFirst = 4;
    private int nextLast = 5;
    private int size;

    public ArrayDeque() {
        this.items = (Tifa[]) new Object[8];
    }
    public void resize(int capacity) {
        Tifa[] temp = (Tifa[]) new Object[capacity];
        int frontLength = this.nextFirst - 0;
        int backLength = this.items.length - (this.nextLast + 1);
        System.arraycopy(this.items, 0, temp, 0, frontLength);
        System.arraycopy(this.items, nextLast + 1, temp, temp.length - backLength , backLength);
        this.items = temp;
        this.nextLast = this.nextLast + capacity / 2;
    }

    public void addFirst(Tifa x) {
        items[nextFirst] = x;
        this.nextFirst = (this.nextFirst - 1) % items.length;
        if (this.nextFirst < 0) {
            this.nextFirst = this.nextFirst + items.length;
        }
        this.size += 1;
        if (this.nextFirst == this.nextLast) {
            this.resize(2 * items.length);
        }
    }
    public void addLast(Tifa x) {
        items[nextLast] = x;
        this.nextLast = (this.nextLast + 1) % items.length;
        this.size += 1;
        if (this.nextFirst == this.nextLast) {
            this.resize(2 * items.length);
        }
    }
    public Tifa get(int i) {

    }
    public int size() {
        return this.size;
    }
    public static void main(String[] args) {
        ArrayDeque<String> ard = new ArrayDeque();
        ard.addLast("a");
        ard.addLast("b");
        ard.addFirst("d");
        ard.addFirst("e");
        ard.addFirst("c");
        ard.addFirst("f");
        ard.addFirst("g");;
    }
}
