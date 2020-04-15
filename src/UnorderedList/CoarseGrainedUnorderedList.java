package UnorderedList;

public class CoarseGrainedUnorderedList<T> extends UnorderedList<T> {

  @Override
  public synchronized boolean add(T item) {
    return super.add(item);
  }

  @Override
  public synchronized boolean remove(T item){
    return super.remove(item);
  }

  @Override
  public synchronized boolean contains(T item) {
    return super.contains(item);
  }

  @Override
  public synchronized int size() {
    return super.size();
  }

  @Override
  public synchronized boolean isEmpty() {
    return super.isEmpty();
  }

  @Override
  public synchronized String toString() {
    return super.toString();
  }
}
