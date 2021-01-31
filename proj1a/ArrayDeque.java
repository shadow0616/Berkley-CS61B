public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst = 3;
    private int nextLast = 4;
    private int size;

    public ArrayDeque() {
        this.items = (T[]) new Object[8];
    }
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
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
    private int minusOne(int index) {
        index = (index - 1) % items.length;
        if (index < 0) {
            index = index + items.length;
        }
        return index;
    }
    private int plusOne(int index) {
        index = (index + 1) % items.length;
        return index;
    }
    public void addFirst(T x) {
        this.items[nextFirst] = x;
        this.nextFirst = this.minusOne(nextFirst);
        if (this.minusOne(nextFirst) == this.nextLast) {
            this.resize(2 * items.length);
        }
        this.size += 1;
    }
    public void addLast(T x) {
        this.items[nextLast] = x;
        this.nextLast = this.plusOne(this.nextLast);
        if (this.nextFirst == this.plusOne(this.nextLast)) {
            this.resize(2 * items.length);
        }
        this.size += 1;
    }
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        this.nextFirst = this.plusOne(this.nextFirst);
        T result = this.items[nextFirst];
        this.items[nextFirst] = null;
        this.size -= 1;
        if (this.size < this.items.length / 4 && this.items.length > 8) {
            this.resize(items.length / 2);
        }
        return result;
    }
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        this.nextLast = this.minusOne(this.nextLast);
        T result = this.items[this.nextLast];
        this.items[nextLast] = null;
        this.size -= 1;
        if (this.size < this.items.length / 4 && this.items.length > 8) {
            this.resize(items.length / 2);
        }
        return result;
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
    public T get(int index) {
        if (index < 0 || index > this.size) {
            return null;
        }
        index = (this.nextFirst + index + 1) % this.items.length;
        return this.items[index];
    }
    public int size() {
        return this.size;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
//    public static void main(String[] args) {
//        ArrayDeque<String> ard = new ArrayDeque();
//        ard.addLast("a");
//        ard.addLast("b");
//        ard.addFirst("d");
//        ard.addFirst("e");
//        ard.addFirst("c");
//        ard.addFirst("f");
//        ard.addFirst("g");
//        ard.addLast("x");
//        ard.printDeque();
//        ard.get(4);
//    }
}
