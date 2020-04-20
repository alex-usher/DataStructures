package OrderedSet;

public interface LockableNode<T> {

  void lock();

  void unlock();

  T item();

  int key();

  LockableNode<T> next();

  void setItem(T item);

  void setKey(int key);

  void setNext(LockableNode<T> next);
}
