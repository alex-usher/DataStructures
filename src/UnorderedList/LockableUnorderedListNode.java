package UnorderedList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockableUnorderedListNode<T> implements LockableNode<T> {
  private T item;
  private LockableNode<T> next;

  private final Lock lock = new ReentrantLock();

  public LockableUnorderedListNode(T item, LockableNode<T> next) {
    this.next = next;
    this.item = item;
  }

  public LockableUnorderedListNode(T item) {
    this.item = item;
  }

  @Override
  public void lock() {
    lock.lock();
  }

  @Override
  public void unlock() {
    lock.unlock();
  }

  @Override
  public T item() {
    return item;
  }

  @Override
  public LockableNode<T> next() {
    return next;
  }

  @Override
  public void setItem(T item) {
    this.item = item;
  }

  @Override
  public void setNext(LockableNode<T> next) {
    this.next = next;
  }
}
