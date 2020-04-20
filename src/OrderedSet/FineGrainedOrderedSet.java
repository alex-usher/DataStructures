package OrderedSet;

import OrderedList.FineGrainedOrderedList;

import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedOrderedSet<T> implements OrderedSetStructure<T> {

  private final LockableNode<T> start;
  private final LockableNode<T> end;
  private final AtomicInteger size = new AtomicInteger(0);

  public FineGrainedOrderedSet() {
    this.start = new LockableOrderedSetNode<T>(null, Integer.MIN_VALUE, null);
    this.end = new LockableOrderedSetNode<T>(null, Integer.MAX_VALUE, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    LockableNode<T> pred = start;
    pred.lock();
    LockableNode<T> curr = pred.next();
    curr.lock();

    while (curr != end && curr.key() < item.hashCode()) {
      pred.unlock();
      pred = curr;
      curr = curr.next();
      curr.lock();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    Point<T> point = find(item);
    LockableNode<T> pred = point.pred;
    LockableNode<T> curr = point.curr;

    try {
      if (curr.key() == item.hashCode()) {
        return false;
      }

      LockableNode<T> newNode = new LockableOrderedSetNode<T>(item);
      pred.setNext(newNode);
      newNode.setNext(curr);
      size.incrementAndGet();
      return true;
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  @Override
  public boolean remove(T item) {
    if (isEmpty()) {
      return false;
    } else {
      Point<T> point = find(item);
      LockableNode<T> pred = point.pred;
      LockableNode<T> curr = point.curr;

      try {
        if (curr.key() == item.hashCode()) {
          pred.setNext(curr.next());
          size.decrementAndGet();
          return true;
        } else {
          return false;
        }
      } finally {
        pred.unlock();
        curr.unlock();
      }
    }
  }

  @Override
  public boolean contains(T item) {
    Point<T> point = find(item);
    try {
      return find(item).curr.key() == item.hashCode();
    } finally {
      point.pred.unlock();
      point.curr.unlock();
    }
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public int size() {
    return size.get();
  }

  private static class Point<T> {
    private final LockableNode<T> pred;
    private final LockableNode<T> curr;

    public Point(LockableNode<T> pred, LockableNode<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }

  @Override
  public synchronized String toString() {
    StringBuilder sb = new StringBuilder();
    LockableNode<T> curr = start.next();

    sb.append("[");

    while (curr != end) {
      sb.append(curr.item().toString());

      if (curr.next() != end) {
        sb.append(", ");
      }

      curr = curr.next();
    }

    sb.append("]");
    return sb.toString();
  }
}
