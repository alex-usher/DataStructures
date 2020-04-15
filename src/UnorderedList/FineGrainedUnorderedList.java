package UnorderedList;

import OrderedList.FineGrainedOrderedList;

import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedUnorderedList<T> implements UnorderedListStructure<T> {

  private final LockableNode<T> start;
  private final LockableNode<T> end;
  private final AtomicInteger size = new AtomicInteger(0);

  public FineGrainedUnorderedList() {
    this.start = new LockableUnorderedListNode<T>(null, null);
    this.end = new LockableUnorderedListNode<T>(null, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    LockableNode<T> pred = start;
    pred.lock();
    LockableNode<T> curr = pred.next();
    curr.lock();

    while(curr != end) {
      if(curr.item().equals(item)) {
        break;
      }

      pred.unlock();
      pred = curr;
      curr = curr.next();
      curr.lock();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    LockableNode<T> pred = start;
    pred.lock();
    LockableNode<T> curr = pred.next();
    curr.lock();

    try {
      while (curr != end) {
        pred.unlock();
        pred = curr;
        curr = curr.next();
        curr.lock();
      }

      LockableNode<T> newNode = new LockableUnorderedListNode<T>(item, curr);
      pred.setNext(newNode);
      size.incrementAndGet();
      return true;
    } finally {
      pred.unlock();
      curr.unlock();
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
        if(curr.item().equals(item)) {
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
    return find(item).curr != end;
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
    LockableNode<T> curr = start.next();
    StringBuilder sb = new StringBuilder();

    while(curr != end) {
      sb.append(String.format("%s ", curr.item()));
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
