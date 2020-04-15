package OrderedList;

import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedOrderedList<T> implements OrderedListStructure<T> {

  private final LockableNode<T> start;
  private final LockableNode<T> end;
  private final AtomicInteger size = new AtomicInteger(0);

  public FineGrainedOrderedList() {
    this.start = new LockableOrderedListNode<T>(null, Integer.MIN_VALUE, null);
    this.end = new LockableOrderedListNode<T>(null, Integer.MAX_VALUE, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    LockableNode<T> pred = start;
    pred.lock();
    LockableNode<T> curr = pred.next();
    curr.lock();

    int key = item.hashCode();

    while (curr.key() < key) {
      pred.unlock();
      pred = curr;
      curr = curr.next();
      curr.lock();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    if (isEmpty()) {
      start.lock();
      end.lock();
      LockableNode<T> newNode = new LockableOrderedListNode<T>(item, item.hashCode(), end);

      try {
        start.setNext(newNode);
        size.incrementAndGet();
        return true;
      } finally {
        start.unlock();
        end.unlock();
      }
    } else {
      Point<T> point = find(item);
      LockableNode<T> pred = point.pred;
      LockableNode<T> curr = point.curr;

      try {
        LockableNode<T> newNode = new LockableOrderedListNode<T>(item, item.hashCode(), curr);
        pred.setNext(newNode);

        size.incrementAndGet();
        return true;
      } finally {
        pred.unlock();
        curr.unlock();
      }
    }
  }

  @Override
  public boolean remove(T item) {
    if(isEmpty()) {
      return false;
    } else {
      Point<T> point = find(item);
      LockableNode<T> pred = point.pred;
      LockableNode<T> curr = point.curr;

      try {
        if(curr.key() == item.hashCode()) {
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
      return point.curr.key() == item.hashCode();
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

  @Override
  public synchronized String toString() {
    StringBuilder sb = new StringBuilder();
    LockableNode<T> curr = start.next();

    while (curr != end) {
      sb.append(String.format("%s ", curr.item().toString()));
      curr = curr.next();
    }

    return sb.toString();
  }

  private static class Point<T> {
    private final LockableNode<T> pred;
    private final LockableNode<T> curr;

    public Point(LockableNode<T> pred, LockableNode<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
