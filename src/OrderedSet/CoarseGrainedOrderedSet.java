package OrderedSet;

public class CoarseGrainedOrderedSet<T> extends OrderedSet<T> {
  @Override
  public synchronized boolean add(T item) {
    return super.add(item);
  }

  @Override
  public synchronized boolean remove(T item) {
    return super.remove(item);
  }

  @Override
  public synchronized boolean contains(T item) {
    return super.contains(item);
  }

  @Override
  public synchronized boolean isEmpty() {
    return super.isEmpty();
  }

  @Override
  public synchronized int size() {
    return super.size();
  }
}
