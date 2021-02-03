public class ArrayDeque<T> {
    /** initializes the array, the array will be filled starting from the middle, track the size */
    private T[] items;
    private int nextFirst = 3;
    private int nextLast = 4;
    private int size;

    /** array constructor, the starting size of the array is 8 */
    public ArrayDeque() {
        this.items = (T[]) new Object[8];
    }

    /** resizes the array, if the first index is smaller than the last index, patch or crop the
     * array in the front and back by half the changed size. if the first index is larger than
     * the last index, insert or remove the changed size between the two indices
     */
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

    /** calculates minus one of the given index so it cycles within the length of the array */
    private int minusOne(int index) {
        index = (index - 1) % items.length;
        if (index < 0) {
            index = index + items.length;
        }
        return index;
    }

    /** calculates plus one of the given index so it cycles within the length of the array */
    private int plusOne(int index) {
        index = (index + 1) % items.length;
        return index;
    }

    /** adds an item to the front, after that, if the indices are adjacent, doubles the size of the
     * array
     */
    public void addFirst(T x) {
        this.items[nextFirst] = x;
        this.nextFirst = this.minusOne(nextFirst);
        if (this.minusOne(nextFirst) == this.nextLast) {
            this.resize(2 * items.length);
        }
        this.size += 1;
    }

    /** adds an item to the end, after that, if the indices are adjacent, doubles the size of the
     * array
     */
    public void addLast(T x) {
        this.items[nextLast] = x;
        this.nextLast = this.plusOne(this.nextLast);
        if (this.nextFirst == this.plusOne(this.nextLast)) {
            this.resize(2 * items.length);
        }
        this.size += 1;
    }

    /** removes and return the first item, after that, if the usage is lower than a quarter, halves
     *  the size of the array. returns null if no such item
     */
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

    /** removes and returns the last item, after that, if the usage is lower than a quarter, halves
     * the size of the array. returns null if no such item
     */
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

    /** print the items in the deque, separated by a space */
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

    /** returns the item at the given index, 0 means the first item, returns null if no such item */
    public T get(int index) {
        if (index < 0 || index > this.size) {
            return null;
        }
        index = (this.nextFirst + index + 1) % this.items.length;
        return this.items[index];
    }

    /** returns the size of the array */
    public int size() {
        return this.size;
    }

    /** returns true of false indicating if the array is empty */
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
