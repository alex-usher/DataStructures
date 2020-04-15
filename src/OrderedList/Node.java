package OrderedList;

public interface Node<T> {

  T item();

  int key();

  Node<T> next();

  void setNext(Node<T> next);

  void setItem(T item);

  void key(int key);
}
