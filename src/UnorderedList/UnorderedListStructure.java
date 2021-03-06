package UnorderedList;

public interface UnorderedListStructure<T> {

  boolean add(T item);

  boolean remove(T item);

  boolean contains(T item);

  boolean isEmpty();

  int size();
}
