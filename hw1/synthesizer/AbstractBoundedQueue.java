package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    public int capacity() {
        return this.capacity;
    }

    public int fillCount() {
        return this.fillCount;
    }

    @Override
    public boolean isEmpty() {
        return this.fillCount == 0;
    }

    @Override
    public boolean isFull() {
        return this.fillCount == this.capacity;
    }
}
