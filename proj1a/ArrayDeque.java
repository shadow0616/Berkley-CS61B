public class ArrayDeque<Tifa> {
    private Tifa[] items;
    private int nextFirst = 3;
    private int nextLast = 4;
    private int size;

    public ArrayDeque() {
        this.items = (Tifa[]) new Object[8];
    }
    public void resize(int capacity) {
        Tifa[] temp = (Tifa[]) new Object[capacity];
        if (this.nextFirst < this.nextLast) {
            int copyLength = this.nextLast - this.nextFirst;
            System.arraycopy(this.items, this.nextFirst, temp, capacity / 4, copyLength);
            this.items = temp;
            this.nextFirst = capacity / 4;
            this.nextLast = this.nextFirst + copyLength;
        } else {
            int start = this.nextFirst + 1;
            int copyStart = capacity - (items.length - this.nextFirst - 1);
            int copyLength = items.length - this.nextFirst - 1;
            System.arraycopy(this.items, 0, temp, 0, this.nextLast);
            System.arraycopy(this.items, start, temp, copyStart, copyLength);
            this.items = temp;
            this.nextFirst = capacity - copyLength - 1;
        }
    }
    public int minusOne(int index) {
        index = (index - 1) % items.length;
        if (index < 0) {
            index = index + items.length;
        }
        return index;
    }
    public int plusOne(int index) {
        index = (index + 1) % items.length;
        return index;
    }
    public void addFirst(Tifa x) {
        this.items[nextFirst] = x;
        if (this.minusOne(nextFirst) == this.nextLast) {
            this.resize(2 * items.length);
        }
        this.nextFirst = this.minusOne(nextFirst);
        this.size += 1;
    }
    public void addLast(Tifa x) {
        this.items[nextLast] = x;
        if (this.nextFirst == this.plusOne(this.nextLast)) {
            this.resize(2 * items.length);
        }
        this.nextLast = this.plusOne(this.nextLast);
        this.size += 1;
    }
    public void removeFirst() {
        this.nextFirst = this.plusOne(this.nextFirst);
        this.items[nextFirst] = null;
        this.size -= 1;
        if (this.size < this.items.length / 4 && this.items.length > 8) {
            this.resize(items.length / 2);
        }
    }
    public void removeLast() {
        this.nextLast = this.minusOne(this.nextLast);
        this.items[nextLast] = null;
        this.size -= 1;
        if (this.size < this.items.length / 4 && this.items.length > 8) {
            this.resize(items.length / 2);
        }
    }
    public void printDeque() {
        if (this.isEmpty()) {
            return;
        } else {
            int index = 0;
            while (index < this.size) {
                System.out.print(this.get(index));
                System.out.print(" ");
                index += 1;
            }
        }
    }
    public Tifa get(int index) {
        if (index < 0 || index > this.size) {
            return null;
        }
        index = (this.nextFirst + index) % this.items.length;
        return this.items[index];
    }
    public int size() {
        return this.size;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public static void main(String[] args) {
        ArrayDeque<String> ard = new ArrayDeque();
        ard.addLast("a");
        ard.addLast("b");
        ard.addFirst("d");
        ard.addFirst("e");
        ard.addFirst("c");
        ard.addFirst("f");
        ard.addFirst("g");
        ard.addLast("x");
        ard.printDeque();
//        ard.get(4);
    }
}
