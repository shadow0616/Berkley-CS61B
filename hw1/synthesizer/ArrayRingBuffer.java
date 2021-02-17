package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Iterator for ArrayRingBuffer class. */
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
    }

    private int plusOne(int index) {
        index = (index + 1) % this.capacity;
        return index;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[this.last] = x;
        this.last = this.plusOne(this.last);
        this.fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T pop = this.rb[this.first];
        this.rb[this.first] = null;
        this.fillCount -= 1;
        this.first = this.plusOne(this.first);
        return pop;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer is empty");
        }
        return rb[this.first];
    }

    /**
     * Implement iterator method
     */
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int front;
        private int count;

        public ArrayRingBufferIterator() {
            this.front = first;
            this.count = 0;
        }

        public boolean hasNext() {
            return this.count == fillCount;
        }

        public T next() {
            T item = rb[this.front];
            front = plusOne(front);
            count += 1;
            return item;
        }
    }
    // When you get to part 5, implement the needed code to support iteration.
}


