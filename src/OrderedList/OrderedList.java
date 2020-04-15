package OrderedList;

public class OrderedList<T> implements OrderedListStructure<T> {

  private final Node<T> start;
  private final Node<T> end;
  private int size;

  public OrderedList() {
    this.start = new OrderedListNode<T>(null, Integer.MIN_VALUE, null);
    this.end = new OrderedListNode<T>(null, Integer.MAX_VALUE, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    Node<T> pred = start;
    Node<T> curr = start.next();
    int key = item.hashCode();

    while (curr.key() < key) {
      pred = curr;
      curr = curr.next();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    if (isEmpty()) {
      Node<T> newNode = new OrderedListNode<>(item);
      start.setNext(newNode);
      newNode.setNext(end);
      size++;
      return true;
    } else {
      Point<T> point = find(item);
      Node<T> curr = point.curr;
      Node<T> pred = point.pred;
      Node<T> newNode = new OrderedListNode<T>(item);

      pred.setNext(newNode);
      newNode.setNext(curr);

      size++;
      return true;
    }
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Node<T> curr = start.next();

    while (curr != end) {
      sb.append(String.format("%s ", curr.item().toString()));
      curr = curr.next();
    }

    return sb.toString();
  }

  private static class Point<T> {
    private final Node<T> pred;
    private final Node<T> curr;

    public Point(Node<T> pred, Node<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
