public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public boolean isEmpty();
    public void printDeque();
    public int size();
}
