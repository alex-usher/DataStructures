package OrderedSet;

public class OrderedSet<T> implements OrderedSetStructure<T> {

  private final Node<T> start;
  private final Node<T> end;
  private int size;

  public OrderedSet() {
    this.start = new OrderedSetNode<T>(null, Integer.MIN_VALUE, null);
    this.end = new OrderedSetNode<T>(null, Integer.MAX_VALUE, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    Node<T> pred = start;
    Node<T> curr = start.next();

    while (curr != end) {
      if (curr.key() >= item.hashCode()) {
        break;
      }

      pred = curr;
      curr = curr.next();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    Point<T> point = find(item);
    Node<T> pred = point.pred;
    Node<T> curr = point.curr;

    if (curr.key() == item.hashCode()) {
      return false;
    }

    Node<T> newNode = new OrderedSetNode<T>(item);
    pred.setNext(newNode);
    newNode.setNext(curr);
    size++;
    return true;
  }

  @Override
  public boolean remove(T item) {
    if (isEmpty()) {
      return false;
    } else {
      Point<T> point = find(item);
      Node<T> pred = point.pred;
      Node<T> curr = point.curr;

      if (curr.key() == item.hashCode()) {
        pred.setNext(curr.next());
        size--;
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public boolean contains(T item) {
    return find(item).curr.key() == item.hashCode();
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  private static class Point<T> {
    private final Node<T> pred;
    private final Node<T> curr;

    public Point(Node<T> pred, Node<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }

  @Override
  public synchronized String toString() {
    StringBuilder sb = new StringBuilder();
    Node<T> curr = start.next();

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
