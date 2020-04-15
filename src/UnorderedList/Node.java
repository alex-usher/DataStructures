package UnorderedList;

public interface Node<T> {
  T item();

  Node<T> next();

  void setNext(Node<T> next);

  void setItem(T item);
}
