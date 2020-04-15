package OrderedList;

public interface LockableNode<T> {
  void lock();

  void unlock();

  T item();

  int key();

  LockableNode<T> next();

  void setKey(int key);

  void setItem(T item);

  void setNext(LockableNode<T> next);
}
