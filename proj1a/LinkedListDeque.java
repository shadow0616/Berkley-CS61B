public class LinkedListDeque<T> {
    /** uses circular method with one sentinel node to implement Deque structure */
    private TNode sentinel;
    private int size;

    private class TNode {
        private T item;
        private TNode prev;
        private TNode next;

        /** Node constructor for doubly linked list with three inputs (item, previous node,
         * next node) */
        public TNode(T item, TNode prev, TNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        /** returns the item at given index recursively, this is refered by the LinkedListDeque
         * class */
        public T getRecursive(int index) {
            if (index == 0) {
                return this.item;
            } else {
                return this.next.getRecursive(index - 1);
            }
        }
    }

    /** empty Deque constructor, initiates a Deque with a sentinel node containing null with both
     * prev and next pointing at itself */
    public LinkedListDeque() {
        this.sentinel = new TNode(null, null, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }

    /** Deque constructor, initiates a Deque with an item */
//    public LinkedListDeque(T x) {
//        this.sentinel = new TNode(null, null, null);
//        TNode newNode = new TNode(x, this.sentinel, this.sentinel);
//        this.sentinel.next = newNode;
//        this.sentinel.prev = newNode;
//        this.size = 1;
//    }

    /** adds an item to the front of the Deque */
    public void addFirst(T item) {
        this.sentinel.next.prev = new TNode(item, this.sentinel, this.sentinel.next);
        this.sentinel.next = this.sentinel.next.prev;
        this.size += 1;
    }

    /** adds an item to the end of the Deque */
    public void addLast(T item) {
        this.sentinel.prev.next = new TNode(item, this.sentinel.prev, this.sentinel);
        this.sentinel.prev = this.sentinel.prev.next;
        this.size += 1;
    }

    /** removes and returns the first item of the Deque */
    public T removeFirst() {
        if (this.size() == 0) {
            return null;
        } else {
            T result = this.sentinel.next.item;
            this.sentinel.next = this.sentinel.next.next;
            this.sentinel.next.prev = this.sentinel;
            this.size -= 1;
            return result;
        }
    }

    /** removes and returns the last item of the Deque */
    public T removeLast() {
        if (this.size() == 0) {
            return null;
        } else {
            T result = this.sentinel.prev.item;
            this.sentinel.prev = this.sentinel.prev.prev;
            this.sentinel.prev.next = this.sentinel;
            this.size -= 1;
            return result;
        }
    }

    /** returns the item at given index without changing the Deque iteratively, 0 represents the
     * front */
    public T get(int index) {
        if (index < 0 || index > this.size() - 1) {
            return null;
        } else {
            TNode pointer = this.sentinel.next;
            while (index != 0) {
                pointer = pointer.next;
                index -= 1;
            }
            return pointer.item;
        }
    }

    //** returns the item at given index recursively without changing the Deque, refers to a helper
    // method in Node class */
    public T getRecursive(int index) {
        if (index < 0 || index > this.size() - 1) {
            return null;
        } else if (index == 0) {
            return this.sentinel.next.item;
        } else {
            return this.sentinel.next.next.getRecursive(index - 1);
        }
    }

    /** print the items in the Deque separated by a space, print null if Deque is empty */
    public void printDeque() {
        TNode pointer = this.sentinel.next;
        System.out.print(pointer.item);
        System.out.print(" ");
        while (pointer.next != this.sentinel) {
            pointer = pointer.next;
            System.out.print(pointer.item);
            System.out.print(" ");
        }
    }

    /** return the size of the Deque */
    public int size() {
        return this.size;
    }

    /** check if the Deque is empty */
    public boolean isEmpty() {
        return this.size == 0;
    }
}
