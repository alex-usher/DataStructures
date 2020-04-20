package OrderedSet;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockableOrderedSetNode<T> implements LockableNode<T> {

  private LockableNode<T> next;
  private T item;
  private int key;

  private final Lock lock = new ReentrantLock();

  public LockableOrderedSetNode(T item, int key, LockableNode<T> next) {
    this.next = next;
    this.item = item;
    this.key = key;
  }

  public LockableOrderedSetNode(T item, int key) {
    this(item, key, null);
  }

  public LockableOrderedSetNode(T item) {
    this(item, item.hashCode(), null);
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
  public int key() {
    return key;
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
  public void setKey(int key) {
    this.key = key;
  }

  @Override
  public void setNext(LockableNode<T> next) {
    this.next = next;
  }
}
