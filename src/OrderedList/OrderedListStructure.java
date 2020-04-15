package OrderedList;

public interface OrderedListStructure<T> {

  boolean add(T item);

  boolean remove(T item);

  boolean contains(T item);

  boolean isEmpty();

  int size();
}
