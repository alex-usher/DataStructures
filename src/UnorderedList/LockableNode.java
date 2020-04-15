package UnorderedList;

public interface LockableNode<T> {

  void lock();

  void unlock();

  T item();

  LockableNode<T> next();

  void setItem(T item);

  void setNext(LockableNode<T> next);
}
