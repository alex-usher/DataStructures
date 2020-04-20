package OrderedSet;

public interface Node<T> {

  T item();

  int key();

  Node<T> next();

  void setNext(Node<T> next);

  void setKey(int key);

  void setItem(T item);
}
